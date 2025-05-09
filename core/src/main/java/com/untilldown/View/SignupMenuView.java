package com.untilldown.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilldown.Controller.SignupMenuController;
import com.untilldown.Model.GameAssetManager;

public class SignupMenuView implements Screen {
    private Skin skin;
    private Stage stage;
    private TextField usernameField;
    private TextField passwordField;
    private TextButton signUpButton;
    private TextButton backButton;
    private Label errorMessage;

    private Label securityQuestion;
    private TextField securityAnswerField;
    private TextButton confirmAnswerButton;

    private final SignupMenuController controller;

    public void showSecurityQuestionDialog() {
        Dialog dialog = new Dialog("Security Question", skin);


        Label questionLabel = new Label("What is your favorite Club?", skin);
        TextField answerField = new TextField("", skin);
        answerField.setMessageText("Your answer");

        Label errorLabel = new Label("", skin);
        errorLabel.setColor(1, 0, 0, 1);

        dialog.getContentTable().add(questionLabel).padTop(10).padLeft(10).padRight(10).row();
        dialog.getContentTable().add(answerField).width(300).padBottom(10).row();
        dialog.getContentTable().add(errorLabel).padBottom(10).row();

        // Add custom confirm and skip buttons
        TextButton confirmButton = new TextButton("Confirm", skin);
        TextButton skipButton = new TextButton("Skip", skin);

        confirmButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (answerField.getText().trim().length() < 2) {
                    errorLabel.setText("Answer is too short.");
                } else {
                    dialog.hide();
                    controller.handleConfirmAnswerInput(answerField.getText().trim());
                }
            }
        });

        skipButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToMainMenu();
            }
        });

        dialog.getContentTable().center();
        dialog.getButtonTable().add(confirmButton).padBottom(5).row();
        dialog.getButtonTable().add(skipButton).padBottom(5).row();

        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.show(stage);
        dialog.center();
        dialog.getTitleTable().padTop(20).padBottom(20);
        dialog.getButtonTable().center();
        dialog.getTitleLabel().setFontScale(1.2f);
        dialog.getTitleLabel().setAlignment(Align.center);
//        float width = stage.getWidth() * 0.4f;
//        float height = stage.getHeight() * 0.3f;
//        dialog.setSize(width, height);
    }

    public SignupMenuView(SignupMenuController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);
        this.skin = skin;

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        signUpButton = new TextButton("Sign Up", skin);
        backButton = new TextButton("Back", skin);
        errorMessage = new Label("", skin);
        errorMessage.setColor(Color.RED);

        securityQuestion = new Label("What is your pet’s name?", skin);
        securityAnswerField = new TextField("", skin);
        confirmAnswerButton = new TextButton("Confirm", skin);

        // Set invisible what should be invisible at beginning
        securityQuestion.setVisible(false);
        securityAnswerField.setVisible(false);
        confirmAnswerButton.setVisible(false);
        errorMessage.setVisible(false);

        signUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleRegisterInput(
                    usernameField.getText(),
                    passwordField.getText()
                );
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.backToStartMenu();
            }
        });

        confirmAnswerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleConfirmAnswerInput(securityAnswerField.getText());
            }
        });

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(new Label("Username:", GameAssetManager.getGameAssetManager().getSkin()));
        table.row();
        table.add(usernameField).width(300);
        table.row().padTop(10);

        table.add(new Label("Password:", GameAssetManager.getGameAssetManager().getSkin()));
        table.row();
        table.add(passwordField).width(300);
        table.row().padTop(10);

        table.add(errorMessage);
        table.row().padTop(10);

        table.add(signUpButton).pad(10);
        table.row();
        table.add(backButton).pad(10);
        table.row().padTop(20);

        stage.addActor(table);
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
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    // Getters for controller
    public TextButton getSignUpButton() { return signUpButton; }
    public TextButton getBackButton() { return backButton; }
    public TextField getUsernameField() { return usernameField; }
    public TextField getPasswordField() { return passwordField; }

    public Label getSecurityQuestion() { return securityQuestion; }
    public TextField getSecurityAnswerField() { return securityAnswerField; }
    public TextButton getConfirmAnswerButton() { return confirmAnswerButton; }

    public void showErrorMessage(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
    }

}
