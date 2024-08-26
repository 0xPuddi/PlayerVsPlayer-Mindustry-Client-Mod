package PlayerVsPlayer.ui;

import mindustry.ui.fragments.MenuFragment.MenuButton;
import arc.scene.style.Drawable;

public class ProfileButton extends MenuButton {
    public MenuButton profileButton;

    public ProfileButton(String text, Drawable icon, Runnable function) {
        super(text, icon, function);
    }
}
