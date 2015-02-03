package lbt05.com.pingpong;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.AVUtils;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;


public class LoginActivity extends ActionBarActivity {
  EditText username;
  EditText password;

  Button login;
  Button signUp;
  Bundle startBundle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    username = (EditText) findViewById(R.id.login_username);
    password = (EditText) findViewById(R.id.login_password);
    login = (Button) findViewById(R.id.login_submit);
    signUp = (Button) findViewById(R.id.signup_submit);

    final Intent startIntent = getIntent();
    startBundle = startIntent.getExtras();

    this.getSupportActionBar().hide();

    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        if (AVUtils.isBlankString(usernameText)) {
          username.setError(getResources().getString(R.string.empty_username_error));
          username.requestFocus();
          return;
        }
        if (AVUtils.isBlankString(passwordText)) {
          password.setError(getResources().getString(R.string.empty_password_error));
          password.requestFocus();
          return;
        }

        AVUser.logInInBackground(usernameText, passwordText, new LogInCallback<AVUser>() {
          @Override
          public void done(AVUser avUser, AVException e) {
            if (e == null) {
              AVInstallation.getCurrentInstallation().put("userId", avUser.getObjectId());
              AVInstallation.getCurrentInstallation().put("device", "Android");
              AVInstallation.getCurrentInstallation().saveInBackground();
              Intent i = new Intent(LoginActivity.this, MainActivity.class);
              if (startBundle != null) {
                i.putExtras(startBundle);
              }
              startActivity(i);
            } else {
              password.setError(e.getMessage());
              password.requestFocus();
            }
          }
        });
      }
    });
    signUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        if (AVUtils.isBlankString(usernameText)) {
          username.setError(getResources().getString(R.string.empty_username_error));
          username.requestFocus();
          return;
        }
        if (AVUtils.isBlankString(passwordText)) {
          password.setError(getResources().getString(R.string.empty_password_error));
          password.requestFocus();
          return;
        }
        AVUser user = new AVUser();
        user.setUsername(usernameText);
        user.setPassword(passwordText);
        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(AVException e) {
            if (e == null) {
              AVInstallation.getCurrentInstallation().put("userId",
                  AVUser.getCurrentUser().getObjectId());
              AVInstallation.getCurrentInstallation().put("device", "Android");
              AVInstallation.getCurrentInstallation().saveInBackground();
              Intent i = new Intent(LoginActivity.this, MainActivity.class);
              if (startBundle != null) {
                i.putExtras(startBundle);
              }
              startActivity(i);
            } else {
              password.setError(e.getMessage());
              password.requestFocus();
            }
          }
        });
      }
    });
    if (AVUser.getCurrentUser() != null && AVUser.getCurrentUser().isAuthenticated()) {
      Intent i = new Intent(LoginActivity.this, MainActivity.class);
      if (startBundle != null) {
        i.putExtras(startBundle);
      }
      startActivity(i);
    }
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_activity, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    // noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
