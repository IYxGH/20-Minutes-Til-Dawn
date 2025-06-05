package com.untilldown.View.MenusViewInMain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle; // For file operations
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture; // To load image textures
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array; // NEW: For tracking textures to dispose
import com.badlogic.gdx.utils.ScreenUtils;
import com.untilldown.Controller.MainMenuController;
import com.untilldown.Controller.MenuControllersInMain.ProfileController;
import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.Enums.AvatarType;
import com.untilldown.Model.Enums.Message;
import com.untilldown.Model.User;
import com.untilldown.View.MainMenuView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ProfileView implements Screen {
    private final ProfileController controller;
    private final Skin skin;
    private Stage stage;
    private float height;
    private float width;

    private TextButton changeUsernameButton;
    private TextButton changePasswordButton;
    private TextButton changeAvatarButton;
    private TextButton deleteAccountButton;
    private TextButton backButton;

    private Drawable defaultPlaceholderAvatarDrawable;

    // Error labels (shared or specific to dialogs)
    private Label errorDialogLabel;

    private Image mainAvatarDisplay; // Main avatar display on the profile screen
    private Label avatarStatusLabel; // Status label on the profile screen

    // NEW: List to keep track of dynamically loaded textures for disposal
    private Array<Texture> disposableTextures = new Array<>();


    public ProfileView(ProfileController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);
        this.skin = skin;
        this.height = Gdx.graphics.getHeight();
        this.width = Gdx.graphics.getWidth();

        // Stage is initialized here, it should be done once in the constructor.
        // The `stage = new Stage()` in `show()` will be removed.
        stage = new Stage();

        // --- QUICK FIX FOR default-placeholder-avatar ---
        // Create a 1x1 white pixel texture programmatically
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE); // You can choose any color, e.g., Color.GRAY
        pixmap.fill();
        Texture placeholderTexture = new Texture(pixmap); // Create texture from pixmap
        pixmap.dispose(); // Dispose the pixmap as it's no longer needed after creating texture

        // Create a drawable from the texture
        defaultPlaceholderAvatarDrawable = new TextureRegionDrawable(new TextureRegion(placeholderTexture));
        disposableTextures.add(placeholderTexture); // Add to our list for disposal

        // --- End Quick Fix Setup ---

        changeUsernameButton = new TextButton(Message.CHANGE_USERNAME.getMessage(), skin);
        changePasswordButton = new TextButton(Message.CHANGE_PASSWORD.getMessage(), skin);
        changeAvatarButton = new TextButton(Message.CHANGE_AVATAR.getMessage(), skin);
        deleteAccountButton = new TextButton(Message.DELETE_ACCOUNT.getMessage(), skin);
        backButton = new TextButton(Message.BACK.getMessage(), skin);
        errorDialogLabel = new Label("", skin);
        errorDialogLabel.setColor(Color.RED);

        avatarStatusLabel = new Label("", skin);
        avatarStatusLabel.setColor(Color.YELLOW);

        // Add Listeners
        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showChangeUsername();
            }
        });

        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showChangePassword();
            }
        });

        changeAvatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showChangeAvatar();
            }
        });

        deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showDeleteAccount();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            }
        });
    }

    public void showTemporaryMessage(String message, Color color) {
        Label tempLabel = new Label(message, skin);
        tempLabel.setColor(color);
        tempLabel.setPosition(width / 2 - tempLabel.getWidth() / 2, 10);
        tempLabel.addAction(
            Actions.sequence(
                Actions.fadeIn(0.3f),
                Actions.delay(2f),
                Actions.fadeOut(0.3f),
                Actions.removeActor()
            )
        );
        stage.addActor(tempLabel);
    }

    public void showChangeUsername() {
        Dialog dialog = new Dialog(Message.CHANGE_USERNAME.getMessage(), skin);
        dialog.getTitleLabel().setColor(Color.GREEN);
        dialog.getContentTable().padBottom(5);

        Label usernameLabel = new Label(Message.ENTER_NEW_USERNAME.getMessage(), skin);
        TextField usernameField = new TextField("", skin);
        usernameField.setMessageText(Message.NEW_USERNAME.getMessage());
        TextButton setButton = new TextButton(Message.ENTER_NEW_USERNAME.getMessage(), skin);
        TextButton backButton = new TextButton(Message.BACK.getMessage(), skin);

        dialog.getContentTable().add(usernameLabel).padBottom(5).row();
        dialog.getContentTable().add(usernameField).width(width * 0.3f).pad(4).row();
        dialog.getContentTable().add(errorDialogLabel).row();
        errorDialogLabel.setVisible(false);
        dialog.getContentTable().add(setButton).pad(2).row();
        dialog.getContentTable().add(backButton).pad(2).row();
        dialog.center();
        dialog.getContentTable().center();

        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.show(stage);
        dialog.center();
        dialog.getTitleTable().padTop(20).padBottom(20);
        dialog.getButtonTable().center();
        dialog.getTitleLabel().setFontScale(1.2f);
        dialog.getTitleLabel().setAlignment(Align.center);

        setButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeName(dialog, usernameField.getText());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.remove();
            }
        });
    }

    public void showChangePassword() {
        Dialog dialog = new Dialog(Message.CHANGE_PASSWORD.getMessage(), skin);
        dialog.getTitleLabel().setColor(Color.GREEN);
        dialog.getContentTable().padBottom(5);

        Label passwordLabel = new Label(Message.ENTER_NEW_PASSWORD.getMessage(), skin);
        errorDialogLabel.setText(Message.PASSWORD_IS_WEAK.getMessage());
        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText(Message.NEW_PASSWORD.getMessage());
        TextButton setButton = new TextButton(Message.ENTER_NEW_PASSWORD.getMessage(), skin);
        TextButton backButton = new TextButton(Message.BACK.getMessage(), skin);

        dialog.getContentTable().add(passwordLabel).padBottom(5).row();
        dialog.getContentTable().add(passwordField).width(width * 0.3f).pad(4).row();
        dialog.getContentTable().add(errorDialogLabel).row();
        errorDialogLabel.setVisible(false);
        dialog.getContentTable().add(setButton).pad(2).row();
        dialog.getContentTable().add(backButton).pad(2).row();
        dialog.center();
        dialog.getContentTable().center();

        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.show(stage);
        dialog.center();
        dialog.getTitleTable().padTop(20).padBottom(20);
        dialog.getButtonTable().center();
        dialog.getTitleLabel().setFontScale(1.2f);
        dialog.getTitleLabel().setAlignment(Align.center);

        setButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changePassword(dialog, passwordField.getText());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.remove();
            }
        });
    }

    public void showDeleteAccount() {
        Dialog dialog = new Dialog(Message.DELETE_ACCOUNT.getMessage(), skin);
        dialog.getTitleLabel().setColor(Color.RED);
        dialog.getContentTable().padBottom(10).padTop(10);

        Label confirmationLabel = new Label(Message.ARE_YOU_SURE_DELETE_ACCOUNT.getMessage(), skin);
        confirmationLabel.setAlignment(Align.center);

        TextButton yesButton = new TextButton(Message.YES_BUTTON.getMessage(), skin);
        yesButton.setColor(Color.RED);

        TextButton noButton = new TextButton(Message.NO_BUTTON.getMessage(), skin);
        noButton.setColor(Color.LIGHT_GRAY);

        dialog.getContentTable().add(confirmationLabel).growX().pad(10).row();
        dialog.button(yesButton).pad(5);
        dialog.button(noButton).pad(5);

        dialog.center();
        dialog.getContentTable().center();
        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.show(stage);
        dialog.center();
        dialog.getTitleTable().padTop(20).padBottom(20);
        dialog.getButtonTable().center();
        dialog.getTitleLabel().setFontScale(1.2f);
        dialog.getTitleLabel().setAlignment(Align.center);

        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.deleteCurrentUserAccount(dialog);
            }
        });

        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.remove();
            }
        });
    }

    public void showChangeAvatar() {
        Dialog dialog = new Dialog(Message.CHANGE_AVATAR.getMessage(), skin);
        dialog.getTitleLabel().setColor(Color.ORANGE);
        dialog.getContentTable().pad(10);

        Table predefinedAvatarsTable = new Table(skin);
        predefinedAvatarsTable.defaults().pad(5);

        for (AvatarType type : AvatarType.values()) {

            Image avatarImage = null;
            Texture loadedTexture = null; // NEW: Hold the loaded texture to track for disposal
            try {
                loadedTexture = new Texture(Gdx.files.internal(type.getPath()));
                disposableTextures.add(loadedTexture); // Add to our list for disposal
                avatarImage = new Image(loadedTexture);
            } catch (Exception e) {
                System.err.println("Error loading predefined avatar: " + type.getPath() + " - " + e.getMessage());
                avatarImage = new Image(defaultPlaceholderAvatarDrawable); // Fallback
            }

            TextButton selectButton = new TextButton(type.name(), skin);

            Table avatarOption = new Table(skin);
            avatarOption.add(avatarImage).size(64, 64).row();
            avatarOption.add(selectButton).padTop(5).row();

            final AvatarType selectedType = type;
            selectButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    User currentUser = App.getCurrentUser();
                    if (currentUser != null) {
                        currentUser.setAvatarAssigned(selectedType);
                        currentUser.setCustomAvatarImagePath(null);
                        controller.updateAvatar(currentUser, dialog, Message.AVATAR_CHANGED_SUCCESSFULLY.getMessage());
                    } else {
                        avatarStatusLabel.setText(Message.LOGIN_REQUIRED_AVATAR.getMessage());
                        dialog.remove();
                    }
                }
            });
            predefinedAvatarsTable.add(avatarOption);
        }

        ScrollPane predefinedScrollPane = new ScrollPane(predefinedAvatarsTable, skin);
        predefinedScrollPane.setFadeScrollBars(false);
        predefinedScrollPane.setScrollingDisabled(true, false);

        TextButton chooseCustomFileButton = new TextButton(Message.CHOOSE_AVATAR_FILE.getMessage(), skin);
        chooseCustomFileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleCustomAvatarSelection(dialog);
            }
        });

        dialog.getContentTable().add(new Label(Message.SELECT_PREDEFINED_OR_CUSTOM.getMessage(), skin)).colspan(2).padBottom(10).row();
        dialog.getContentTable().add(predefinedScrollPane).expand().fill().colspan(2).padBottom(10).row();
        dialog.getContentTable().add(chooseCustomFileButton).colspan(2).pad(10).row();

        TextButton backButtonDialog = new TextButton(Message.BACK.getMessage(), skin);
        backButtonDialog.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.remove();
            }
        });
        dialog.getContentTable().add(backButtonDialog).colspan(2).pad(5).row();

        dialog.center();
        dialog.getContentTable().center();
        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.show(stage);
        dialog.center();
        dialog.getTitleTable().padTop(20).padBottom(20);
        dialog.getTitleLabel().setFontScale(1.2f);
        dialog.getTitleLabel().setAlignment(Align.center);
    }

    private void handleCustomAvatarSelection(Dialog avatarDialog) {
        User currentUser = App.getCurrentUser();
        if (currentUser == null) {
            avatarStatusLabel.setText(Message.LOGIN_REQUIRED_AVATAR.getMessage());
            avatarDialog.remove();
            return;
        }

        // Run on UI thread (important for Swing)
        Gdx.app.postRunnable(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select an Image File");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "png", "jpg", "jpeg"));

            int result = fileChooser.showOpenDialog(null);
            if (result != JFileChooser.APPROVE_OPTION) {
                System.out.println("File selection cancelled.");
                return;
            }

            File selectedFile = fileChooser.getSelectedFile();
            FileHandle sourceFile = Gdx.files.absolute(selectedFile.getAbsolutePath());

            if (!sourceFile.exists()) {
                avatarStatusLabel.setText(Message.FILE_NOT_FOUND_ERROR.getMessage() + "\n" + sourceFile.path());
                System.err.println("Selected file not found: " + sourceFile.path());
                return;
            }

            if (sourceFile.isDirectory()) {
                avatarStatusLabel.setText(Message.MUST_BE_IMAGE_FILE.getMessage());
                System.err.println("Selected path is a directory: " + sourceFile.path());
                return;
            }

            String extension = sourceFile.extension().toLowerCase();
            if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("jpeg")) {
                avatarStatusLabel.setText(Message.INVALID_IMAGE_FORMAT.getMessage());
                System.err.println("Unsupported image format: " + sourceFile.path());
                return;
            }

            FileHandle customAvatarsDir = Gdx.files.local("custom_avatars/");
            if (!customAvatarsDir.exists()) {
                customAvatarsDir.mkdirs();
            }

            String uniqueFileName = System.currentTimeMillis() + "_" + sourceFile.name();
            FileHandle destinationFile = customAvatarsDir.child(uniqueFileName);

            try {
                sourceFile.copyTo(destinationFile);
                System.out.println("Avatar copied to: " + destinationFile.path());

                currentUser.setCustomAvatarImagePath(destinationFile.path());
                currentUser.setAvatarAssigned(null);

                controller.updateAvatar(currentUser, avatarDialog, Message.AVATAR_UPLOAD_SUCCESS.getMessage());

            } catch (Exception e) {
                avatarStatusLabel.setText(Message.AVATAR_UPLOAD_FAILED.getMessage());
                System.err.println("Error copying avatar file: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public void updateCurrentAvatarDisplay() {
        User currentUser = App.getCurrentUser();
        if (currentUser == null || mainAvatarDisplay == null) return;

        // Dispose previous texture if it was dynamically loaded
        Drawable currentDrawable = mainAvatarDisplay.getDrawable();
        if (currentDrawable instanceof TextureRegionDrawable) {
            Texture texture = ((TextureRegionDrawable) currentDrawable).getRegion().getTexture();
            // Only dispose if it's NOT the default placeholder texture
            // And if it's one of the textures we explicitly added to our disposableTextures list
            if (texture != ((TextureRegionDrawable)defaultPlaceholderAvatarDrawable).getRegion().getTexture() && disposableTextures.contains(texture, true)) {
                texture.dispose();
                disposableTextures.removeValue(texture, true); // Remove from tracking list
            }
        }


        String avatarPath = currentUser.getAvatarImagePath();
        if (avatarPath != null) {
            Texture avatarTexture = null;
            try {
                // Determine if it's a custom avatar or predefined based on AvatarType
                // This is the more robust check. If AvatarType is CUSTOM_AVATAR, then it's local.
                // Otherwise, it's a predefined type (internal).
                if (currentUser.getAvatarAssigned() == null) {
                    FileHandle customFile = Gdx.files.local(avatarPath);
                    if (customFile.exists()) {
                        avatarTexture = new Texture(customFile);
                        disposableTextures.add(avatarTexture); // Add custom texture to disposal list
                    } else {
                        System.err.println("Custom avatar file not found at local path: " + customFile.path());
                        avatarStatusLabel.setText(Message.CUSTOM_AVATAR_NOT_FOUND.getMessage());
                        // Fallback to placeholder if custom file not found
                        mainAvatarDisplay.setDrawable(defaultPlaceholderAvatarDrawable);
                        return; // Exit after setting fallback
                    }
                } else {
                    // Predefined avatars are internal assets
                    avatarTexture = new Texture(Gdx.files.internal(avatarPath));
                    disposableTextures.add(avatarTexture); // Add predefined texture to disposal list
                }
            } catch (Exception e) {
                System.err.println("Error loading avatar image for display from " + avatarPath + ": " + e.getMessage());
                avatarStatusLabel.setText(Message.ERROR_LOADING_AVATAR.getMessage());
                // Fallback to placeholder if loading fails
                mainAvatarDisplay.setDrawable(defaultPlaceholderAvatarDrawable);
                return; // Exit after setting fallback
            }

            if (avatarTexture != null) {
                mainAvatarDisplay.setDrawable(new Image(avatarTexture).getDrawable());
                // Set size, origin, scale on the *existing* mainAvatarDisplay, not re-creating it.
                mainAvatarDisplay.setSize(128, 128);
                mainAvatarDisplay.setOrigin(Align.center);
                mainAvatarDisplay.setScale(1f);
            } else {
                // Should only reach here if avatarTexture is null after all attempts, which implies an error.
                mainAvatarDisplay.setDrawable(defaultPlaceholderAvatarDrawable);
            }
        } else {
            // If no avatar path is set at all (e.g., brand new user), display generic placeholder
            mainAvatarDisplay.setDrawable(defaultPlaceholderAvatarDrawable);
        }
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        float buttonWidth = width * 0.2f;
        float buttonHeight = height * 0.15f;
        float heightPad = height * 0.02f;

        Table table = new Table(skin);
        table.setFillParent(true);
        table.center();

        // Initialize mainAvatarDisplay once here, it's a class member
        mainAvatarDisplay = new Image(defaultPlaceholderAvatarDrawable);
        mainAvatarDisplay.setSize(128, 128);
        mainAvatarDisplay.setOrigin(Align.center);
        mainAvatarDisplay.setScale(1f);

        table.add(mainAvatarDisplay).size(128, 128).padBottom(20).row();

        table.add(changeUsernameButton).width(buttonWidth).height(buttonHeight).expandX().fillX();
        table.row().pad(heightPad);
        table.add(changePasswordButton).width(buttonWidth).height(buttonHeight);
        table.row().pad(heightPad);
        table.add(changeAvatarButton).width(buttonWidth).height(buttonHeight);
        table.row().pad(heightPad);
        table.add(deleteAccountButton).width(buttonWidth).height(buttonHeight);
        table.row().pad(heightPad);
        table.add(backButton).width(buttonWidth).height(buttonHeight);
        table.row().pad(heightPad);

        table.add(avatarStatusLabel).colspan(1).padTop(10).row();

        stage.addActor(table);

        // Update the display for the current user's avatar when the screen is shown
        updateCurrentAvatarDisplay();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();

        // Dispose all dynamically loaded textures
        for (Texture texture : disposableTextures) {
                texture.dispose();
        }
        disposableTextures.clear(); // Clear the list
    }

    public ProfileController getController() { return controller; }
    public Skin getSkin() { return skin; }
    public Stage getStage() { return stage; }
    public void setStage(Stage stage) { this.stage = stage; }
    public float getHeight() { return height; }
    public void setHeight(float height) { this.height = height; }
    public float getWidth() { return width; }
    public void setWidth(float width) { this.width = width; }
    public TextButton getChangeUsernameButton() { return changeUsernameButton; }
    public void setChangeUsernameButton(TextButton changeUsernameButton) { this.changeUsernameButton = changeUsernameButton; }
    public TextButton getChangePasswordButton() { return changePasswordButton; }
    public void setChangePasswordButton(TextButton changePasswordButton) { this.changePasswordButton = changePasswordButton; }
    public TextButton getChangeAvatarButton() { return changeAvatarButton; }
    public void setChangeAvatarButton(TextButton changeAvatarButton) { this.changeAvatarButton = changeAvatarButton; }
    public TextButton getDeleteAccountButton() { return deleteAccountButton; }
    public void setDeleteAccountButton(TextButton deleteAccountButton) { this.deleteAccountButton = deleteAccountButton; }
    public TextButton getBackButton() { return backButton; }
    public void setBackButton(TextButton backButton) { this.backButton = backButton; }
    public Label getErrorDialogLabel() { return errorDialogLabel; }
    public void setErrorDialogLabel(Label errorDialogLabel) { this.errorDialogLabel = errorDialogLabel; }
}
