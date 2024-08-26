package PlayerVsPlayer.ui;

import PlayerVsPlayer.blockchain.BlockchainClient;
import PlayerVsPlayer.net.NetLobbyConnection;

import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.ui.dialogs.BaseDialog;
import arc.scene.ui.Dialog;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Log;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.graphics.Texture;
import arc.graphics.g2d.TextureRegion;

public class LobbyDialog extends BaseDialog {
    ProfileDialog profileDialog;

    Table lobbyTable;
    BlockchainClient bcClient;
    BaseDialog dialogQR;
    BaseDialog dialogKicked;

    String uuid = null;
    String name = null;
    String bettingAddress = null;
    Double bettingBalance = null;
    String bettingConfirmed = "false";

    String gameId = null;

    Integer players;
    String[] playersName;
    String[] playersBetAmount;
    String[] playersConfirmed;

    int port = 0;
    String host = "";
    NetLobbyConnection nlConnection = null;
    private Thread nlConnectionThread = null;

    public LobbyDialog(BlockchainClient _bcClient, ProfileDialog _profileDialog) {
        super("Lobby");
        dialogQR = new BaseDialog("Bet QR");
        dialogKicked = new BaseDialog("Kicked");

        this.bcClient = _bcClient;
        this.profileDialog = _profileDialog;

        this.name = Core.settings.getString("name");

        this.addCloseButton();

        this.cont.table(table -> {
            lobbyTable = table;
        });
    }

    /*
     * lobbyDataReceived takes the first packet upon connection. Initializes lobby
     * variables and shows the lobby
     */
    public void lobbyDataReceived(String s) {
        // Split the input string into sections based on field delimiters
        String[] sections = s.split("(names:|betamount:|confirmed:|gameid:|socketinfo:|uuidUserServer:)");

        // Extract values for names, betamount, and confirmed
        playersName = sections[1].split("\\?");
        playersBetAmount = sections[2].split("\\?");
        playersConfirmed = sections[3].split("\\?");
        gameId = sections[4];
        host = sections[5].split("\\?")[0];
        port = Integer.parseInt(sections[5].split("\\?")[1]);
        uuid = sections[6];

        players = this.playersName.length;
        rebuildTable();

        if (!isShown()) {
            this.show();
        }
    }

    /*
     * rebuildTable clears the table and recreates it with up to date values. It
     * should be called whenever playersName, playersBetAmount, playersConfirmed
     * or bettingBalance are changed.
     */
    private void rebuildTable() {
        // fetch any updates
        this.name = Core.settings.getString("name");
        this.bettingAddress = this.profileDialog.getUserAddress();

        for (Integer i = 0; i < this.players; ++i) {
            if (name.equals(this.playersName[i])) {
                this.bettingBalance = convertToDouble(this.playersBetAmount[i]);
                this.bettingConfirmed = this.playersConfirmed[i];
                break;
            }
        }

        lobbyTable.clear();

        // User
        Table pt = new Table();

        // Broken UI
        Table iTable = new Table();
        iTable.add("Game begins after at least 2 player betted").padBottom(20).center();
        pt.add(iTable);
        pt.row();
        //

        pt.add(name);

        if (this.bettingAddress != null) {
            String shortAddress = "";
            shortAddress.concat(this.bettingAddress.substring(0, 4));
            shortAddress.concat("...");
            shortAddress.concat(this.bettingAddress.substring(this.bettingAddress.length() - 2 - 1,
                    this.bettingAddress.length() - 1));

            pt.add(shortAddress).padLeft(7.5f).padRight(7.5f);
        } else {
            pt.field("Address", (addr) -> {
                this.bettingAddress = addr;
            }).padLeft(7.5f).padRight(7.5f).width(250);
        }

        if (this.bettingConfirmed.equals("true")) {
            pt.add("Bet " + this.bettingBalance).padLeft(7.5f).padRight(7.5f);
        } else {
            pt.field("0", (bet) -> {
                if (bet.equals("")) {
                    return;
                }

                if (hasOnlyNumbersAndOneDot(bet)) {
                    this.bettingBalance = convertToDouble(bet);
                }
            }).padLeft(7.5f).padRight(7.5f).width(200);
        }

        if (this.bettingConfirmed.equals("true")) {
            pt.image(Icon.ok).padLeft(7.5f).padRight(7.5f);
        } else {
            pt.button("Bet", () -> {
                if (this.bettingAddress == null || this.bettingBalance == null) {
                    return;
                }

                if (bcClient.isValidAddress(this.bettingAddress)) {
                    betTransaction(this.bettingBalance, this.uuid, this.gameId, this.bettingAddress);
                }

            }).padLeft(7.5f).padRight(7.5f).size(100, 50);
        }

        lobbyTable.add(pt);
        lobbyTable.row();

        // Lobby rows
        lobbyTable.table(list -> {
            list.align(Align.center);
            list.top();
            Table t = new Table();

            ScrollPane pane = new ScrollPane(t);
            list.add(pane).grow();

            for (Integer i = 0; i < this.players; ++i) {
                if (name.equals(this.playersName[i])) {
                    continue;
                }

                t.add(this.playersName[i]).padLeft(7.5f).padRight(7.5f).padTop(25);
                t.add(this.playersBetAmount[i]).padLeft(7.5f).padRight(7.5f).padTop(25);

                if (this.playersConfirmed[i].equals("true")) {
                    t.image(Icon.ok).padLeft(7.5f).padRight(7.5f).padTop(25);
                } else {
                    t.image(Icon.none).padLeft(7.5f).padRight(7.5f).padTop(25);
                }

                t.row();
            }

        });
    }

    /*
     * Initializes the Client lobby and shows the lobby dialog
     */
    @Override
    public Dialog show() {
        // Showing without being connected to updates
        Log.info("show");
        if (this.nlConnection == null) {
            // Estanlish TCP connection with server for further comunication
            nlConnection = new NetLobbyConnection(host, port, uuid);
            nlConnection.addMethodServer("SocketLobbyData", this::socketLobbyDataReceived);
            nlConnection.addMethodServer("GameReady", this::gameReady);
            nlConnection.addMethodServer("Idle", this::idle);
            nlConnection.addMethodServer("HeartbeatKick", this::heartbeatKick);
            nlConnection.addMethodServer("ListenerFailed", this::listenerFailed);

            nlConnectionThread = new Thread(nlConnection);
            nlConnectionThread.start();
        }

        return show(Core.scene);
    }

    /*
     * addCloseButton custom function to add gracefull hiding functionality
     */
    @Override
    public void addCloseButton() {
        buttons.defaults().size(210f, 64f);
        buttons.button("@back", Icon.left, () -> {
            this.hide(true);
        }).size(210f, 64f);

        addCloseListener();
    }

    /*
     * hide is a lobby dialog custom hide function. If it is gracefull it notifies
     * the server, if not it just closes the socket
     */
    public void hide(boolean gracefully) {
        if (gracefully) {
            nlConnection.sendPacket("DeclareForfeit", this.uuid);
        }

        nlConnection.closeConnection(gracefully);
        nlConnection = null;
        nlConnectionThread.interrupt();
        nlConnectionThread = null;

        this.hide();
    }

    /*
     * gameReady is used to connect to the server once prompted
     */
    private void gameReady(String s) {
        this.hide(true);

        // Trigger connection
        Vars.ui.join.connect(host, port);
    }

    /*
     * idle displays the message of the idle kick and closes the socet
     */
    private void idle(String msg) {
        dialogKicked.cont.clear();

        dialogKicked.cont.add("You have been kicked from the lobby because you are idle witouth any bet")
                .color(Color.red).row();
        dialogKicked.cont.add("Once you join a lobby bet to not get kicked out and play").row();

        dialogKicked.cont.button("Close", dialogKicked::hide).size(100f, 50f);
        dialogKicked.show();

        this.hide(false);
    }

    /*
     * heartbeatKick displays the message of the heartbeat kick and closes the
     * socket
     */
    private void heartbeatKick(String msg) {
        dialogKicked.cont.clear();

        dialogKicked.cont.add("You have been kicked from the lobby because your client is IDLE")
                .color(Color.red).row();
        dialogKicked.cont.add("Without any bet")
                .color(Color.red).row();
        dialogKicked.cont.add("We suggest you to contact our support if this happens frequently").row();

        dialogKicked.cont.button("Close", dialogKicked::hide).size(100f, 50f);
        dialogKicked.show();

        this.hide(false);
    }

    /*
     * listenerFailed displays the message of the listener failed kick and closes
     * the sokcet
     */
    private void listenerFailed(String msg) {
        dialogKicked.cont.clear();

        dialogKicked.cont.add("You have been kicked from the lobby because")
                .color(Color.red).row();
        dialogKicked.cont.add("the server listener encountered an error")
                .color(Color.red).row();
        dialogKicked.cont.add("We suggest you to contact our support if this happens frequently").row();

        dialogKicked.cont.button("Close", dialogKicked::hide).size(100f, 50f);
        dialogKicked.show();

        this.hide(false);
    }

    /*
     * betTransaction will generate a Deep Link for the betting transaction
     */
    private void betTransaction(Double betAmount, String playerId, String gameId, String bettingAddress) {
        double addressNativebalance = bcClient.getAddressNativeBalance(bettingAddress);

        if (addressNativebalance != Double.NaN && addressNativebalance < betAmount) {
            dialogQR.cont.clear();
            dialogQR.cont.add("Betting more than actual address balance").color(Color.red).row();
            dialogQR.cont.button("Close", dialogQR::hide).size(100f, 50f);
            dialogQR.show();
            return;
        }

        // Generate QR code with relative data, abort if missing
        Pixmap pixmapQR = this.bcClient.createBetTransaction(betAmount, playerId, gameId);

        // Display qr code to scan
        dialogQR.cont.clear();
        dialogQR.cont.add("Scan the QR and confirm the transaction to bet " + betAmount + " ETH").row();

        if (pixmapQR.height == 0) {
            // Handle QR exceptions
            dialogQR.cont.add("An error occured during QR code generation").color(Color.red).row();
        } else {
            dialogQR.cont.image(new TextureRegion(new Texture(pixmapQR))).pad(20f).row();
        }

        dialogQR.cont.button("Close", dialogQR::hide).size(100f, 50f);
        dialogQR.show();
    }

    /*
     * socketLobbyDataReceived takes lobby socket packages and updates the UI. It is
     * a warmed up function of lobbyDataReceived.
     */
    private void socketLobbyDataReceived(String s) {
        // Split the input string into sections based on field delimiters
        String[] sections = s.split("(names:|betamount:|confirmed:|gameid:)");

        // Extract values for names, betamount, and confirmed
        playersName = sections[1].split("\\?");
        playersBetAmount = sections[2].split("\\?");
        playersConfirmed = sections[3].split("\\?");
        gameId = sections[4]; // not strictly necessary

        players = this.playersName.length;
        rebuildTable();
    }

    /*
     * convertToFloat converts a string to double
     */
    public static Double convertToDouble(String charSet) {
        // Remove leading and trailing whitespaces
        charSet = charSet.trim();

        // Replace leading zeros (if any)
        charSet = charSet.replaceFirst("^0+", "");

        if (charSet.equals(".") || charSet.equals("")) {
            charSet = "0";
        }

        // Convert to Double
        return Double.parseDouble(charSet);
    }

    /*
     * hasOnlyNumbersAndOneDot returns true if a string has only numbers and one
     * comma
     */
    private static boolean hasOnlyNumbersAndOneDot(String input) {
        int dotCount = 0;

        for (char ch : input.toCharArray()) {
            if (Character.isDigit(ch)) {
                // It's a digit
            } else if (ch == '.') {
                // It's a dot
                dotCount++;

                // If more than one comma is found, return false
                if (dotCount > 1) {
                    return false;
                }
            } else {
                // It's neither a digit nor a comma
                return false;
            }
        }

        // Return true only if there is exactly one comma
        return dotCount == 1 || dotCount == 0;
    }
}
