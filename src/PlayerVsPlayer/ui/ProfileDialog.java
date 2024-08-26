package PlayerVsPlayer.ui;

import PlayerVsPlayer.blockchain.BlockchainClient;

import mindustry.ui.dialogs.BaseDialog;

import arc.scene.ui.layout.Table;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.graphics.Texture;
import arc.graphics.g2d.TextureRegion;

public class ProfileDialog extends BaseDialog {
    Table profileTable;
    BlockchainClient bcClient;
    BaseDialog dialogQR;

    String savedAddress = null;
    String currentAddress = "";
    Integer balance = 0;
    Integer airdropPoints = 0;

    public ProfileDialog(BlockchainClient _bClient) {
        super("Profile");
        dialogQR = new BaseDialog("Withdraw QR");

        this.bcClient = _bClient;

        this.addCloseButton();

        this.cont.table(table -> {
            table.field("Address", (addr) -> {
                if (bcClient.isValidAddress(addr)) {
                    currentAddress = addr;
                    balance = getUserContractBalance(addr);
                    airdropPoints = getUserAirdropPoints(addr);
                    rebuildTable();
                }
            });

            table.row();

            table.button("Save Address", () -> {
                if (bcClient.isValidAddress(currentAddress)) {
                    savedAddress = currentAddress;
                    rebuildTable();
                }
            }).padTop(50).size(200, 50);

            table.row();

            table.add("Balance: " + balance).padTop(50);

            table.row();

            table.button("Withdraw", () -> {
                if (savedAddress != null) {
                    withdrawBalanceTransaction(savedAddress);
                    return;
                }

                if (bcClient.isValidAddress(currentAddress)) {
                    withdrawBalanceTransaction(currentAddress);
                }
            }).padTop(50).size(150, 50);

            table.row();

            table.add("Balance: " + balance).padTop(50);

            table.row();

            table.add("Points: " + airdropPoints).padTop(50);

            profileTable = table;
        });
    }

    /*
     * rebuildTable clears the table are recreates it with up to date values
     */
    private void rebuildTable() {
        profileTable.clear();

        if (bcClient.isValidAddress(currentAddress)) {
            profileTable.field(currentAddress, (addr) -> {
                if (bcClient.isValidAddress(addr)) {
                    currentAddress = addr;
                    balance = getUserContractBalance(addr);
                    airdropPoints = getUserAirdropPoints(addr);
                    rebuildTable();
                }
            });
        } else if (savedAddress != null) {
            profileTable.field(savedAddress, (addr) -> {
                if (bcClient.isValidAddress(addr)) {
                    currentAddress = addr;
                    balance = getUserContractBalance(addr);
                    airdropPoints = getUserAirdropPoints(addr);
                    rebuildTable();
                }
            });
        } else {
            profileTable.field("Address", (addr) -> {
                if (bcClient.isValidAddress(addr)) {
                    currentAddress = addr;
                    balance = getUserContractBalance(addr);
                    airdropPoints = getUserAirdropPoints(addr);
                    rebuildTable();
                }
            });
        }

        profileTable.row();

        profileTable.button("Save Address", () -> {
            if (bcClient.isValidAddress(currentAddress)) {
                savedAddress = currentAddress;
                rebuildTable();
            }
        }).padTop(50).size(200, 50);

        profileTable.row();

        profileTable.add("Balance: " + balance).padTop(50);

        profileTable.row();

        profileTable.button("Withdraw", () -> {
            if (savedAddress != null) {
                withdrawBalanceTransaction(savedAddress);
                return;
            }

            if (bcClient.isValidAddress(currentAddress)) {
                withdrawBalanceTransaction(currentAddress);
            }
        }).padTop(50).size(150, 50);

        profileTable.row();

        profileTable.add("Balance: " + balance).padTop(50);

        profileTable.row();

        profileTable.add("Points: " + airdropPoints).padTop(50);
    }

    /*
     * getUserAddress returns the user's saved address, if there is no address
     * returns empty string
     */
    public String getUserAddress() {
        return savedAddress;
    }

    /*
     * getUserContractBalance will retrive the user contract balance
     */
    private Integer getUserContractBalance(String Address) {
        // check addr validity

        // Fetch Balance on address

        // Return points
        return 10;
    }

    /*
     * getUserAirdropPoints will retrive the user airdrop points
     */
    private Integer getUserAirdropPoints(String Address) {
        // check addr validity

        // Fetch Events on address

        // Calculate points

        // Return points
        return 125;
    }

    /*
     * withdrawBalanceTransaction will initiate the transaction for user that re
     * logged in and provide a transaction qr for not logged in users
     */
    private void withdrawBalanceTransaction(String Address) {
        // Generate QR code with relative data, abort if missing
        Pixmap pixmapQR = bcClient.createWithdrawTransaction();

        // Display qr code to scan
        dialogQR.cont.clear();
        dialogQR.cont.add("Scan the QR to withdraw your funds").row();

        if (pixmapQR.height == 0) {
            // Handle QR exceptions
            dialogQR.cont.add("An error occured during QR code generation").color(Color.red).row();
        } else {
            dialogQR.cont.image(new TextureRegion(new Texture(pixmapQR))).pad(20f).row();
        }

        dialogQR.cont.button("Close", dialogQR::hide).size(100f, 50f);
        dialogQR.show();

        // Listen to txn, executed close automatically dialog
        // String contractAddress = bcClient.getContractAddress();
        // ...
    }
}
