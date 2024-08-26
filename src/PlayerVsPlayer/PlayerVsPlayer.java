package PlayerVsPlayer;

import PlayerVsPlayer.ui.ProfileDialog;
import PlayerVsPlayer.ui.ProfileButton;
import PlayerVsPlayer.ui.LobbyDialog;
import PlayerVsPlayer.blockchain.BlockchainClient;

import arc.*;
import arc.util.*;

import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.gen.Icon;

import io.github.cdimascio.dotenv.Dotenv;

public class PlayerVsPlayer extends Mod {
    private Dotenv dotenv;

    private ProfileDialog profileDialog;
    private ProfileButton profileButton;

    private LobbyDialog lobbyDialog;

    public BlockchainClient bcClient;

    public PlayerVsPlayer() {
        Log.info("Loaded PlayerVersusPlayer constructor.");

        // listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            this.bcClient = new BlockchainClient(
                    dotenv.get("RPC_URL"),
                    dotenv.get("CONTRACT_ADDRESS"),
                    dotenv.get("CHAIN_ID"));
            this.profileDialog = new ProfileDialog(bcClient);
            this.profileButton = new ProfileButton("Profile", Icon.players, () -> {
                profileDialog.show();
            });
            this.lobbyDialog = new LobbyDialog(bcClient, this.profileDialog);

            Vars.ui.menufrag.addButton(profileButton);
        });
    }

    @Override
    public void init() {
        dotenv = Dotenv.load();

        Vars.netClient.addPacketHandler("LobbyData", (h) -> {
            Vars.netClient.disconnectQuietly();
            lobbyDialog.lobbyDataReceived(h);
        });

        Log.info(Vars.mods.getModStrings());
    }
}
