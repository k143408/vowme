package com.vowme.vol.app.activities.profile;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.utilities.activities.ProfileFormActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.api.DownloadImageTask;
import com.vowme.app.utilities.customWidgets.CustomSpinnerTextView;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.app.utilities.helpers.ImageHelper;
import com.vowme.vol.app.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BasicInfoActivity extends ProfileFormActivity {
    private static final int PICK_IMAGE_ID = 234;
    private static final String TAG = "ImagePicker";
    private static final String TEMP_BITMAP_NAME = "tempBitmap";
    private static final String TEMP_IMAGE_NAME = "tempImage";
    private static ImageView avatar;
    private final List<String> values = new C08181();
    private File avatarFile;
    private TextView everyDayTitle;
    private TextView firstName;
    private CustomSpinnerTextView gender;
    private TextView idText;
    private TextView lastName;
    private CustomSpinnerTextView title;

    public static Intent getPickImageIntent(Context context) {
        List<Intent> intentList = addIntentsToList(context, new ArrayList(), new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI));
        Intent takePhotoIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        takePhotoIntent.putExtra("return-data", true);
        takePhotoIntent.putExtra("output", Uri.fromFile(getTempFile(context, TEMP_IMAGE_NAME)));
        intentList = addIntentsToList(context, intentList, takePhotoIntent);
        if (intentList.size() <= 0) {
            return null;
        }
        Intent chooserIntent = Intent.createChooser((Intent) intentList.remove(intentList.size() - 1), null);
        chooserIntent.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) intentList.toArray(new Parcelable[0]));
        return chooserIntent;
    }

    private static List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent, 0)) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
        }
        return list;
    }

    private static File getTempFile(Context context, String fileName) {
        File imageFile = new File(context.getExternalCacheDir(), fileName);
        imageFile.getParentFile().mkdirs();
        return imageFile;
    }

    public static File getImageFromResult(Context context, int resultCode, Intent imageReturnedIntent) {
        IOException e;
        File compressImageFile = getTempFile(context, TEMP_BITMAP_NAME);
        if (resultCode == -1) {
            Uri selectedImage;
            File imageFile = getTempFile(context, TEMP_IMAGE_NAME);
            boolean isCamera = imageReturnedIntent == null || imageReturnedIntent.getData() == null || imageReturnedIntent.getData().equals(Uri.fromFile(imageFile));
            if (isCamera) {
                selectedImage = Uri.fromFile(imageFile);
            } else {
                selectedImage = imageReturnedIntent.getData();
            }
            ByteArrayOutputStream bos = ImageHelper
                    .compressedUntilSize(ImageHelper
                            .getScaledRotatedImage(selectedImage.getPath(), context.getResources().getInteger(R.integer.LARGE_SIZE)), 50000, 100);
            byte[] bitmapdata = bos.toByteArray();
            try {
                FileOutputStream fos = new FileOutputStream(compressImageFile);
                FileOutputStream fileOutputStream;
                try {
                    fos.write(bitmapdata);
                    bos.flush();
                    bos.close();
                    fos.flush();
                    fos.close();
                    fileOutputStream = fos;
                } catch (IOException e2) {
                    e = e2;
                    fileOutputStream = fos;
                    e.printStackTrace();
                    avatar.setImageURI(Uri.fromFile(compressImageFile));
                    return compressImageFile;
                }
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                avatar.setImageURI(Uri.fromFile(compressImageFile));
                return compressImageFile;
            }
            avatar.setImageURI(Uri.fromFile(compressImageFile));
        }
        return compressImageFile;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initFields() {
        avatar = (ImageView) findViewById(R.id.image_avatar);
        this.idText = (TextView) findViewById(R.id.volunteer_id);
        this.firstName = (TextView) findViewById(R.id.firstname_txt);
        this.lastName = (TextView) findViewById(R.id.lastname_txt);
        this.everyDayTitle = (TextView) findViewById(R.id.everyday_txt);
        TextInputLayout titleLayout = (TextInputLayout) findViewById(R.id.title_include);
        this.title = (CustomSpinnerTextView) titleLayout.findViewById(R.id.spinner_txt);
        this.title.setUpSpinnerTextView(17367043, DefaultDataHelper.getTitles());
        titleLayout.setHint("Title");
        TextInputLayout genderLayout = (TextInputLayout) findViewById(R.id.gender_include);
        if (!(genderLayout == null || genderLayout.getEditText() == null)) {
            genderLayout.getEditText().addTextChangedListener(getFloatingTextLengthValidator(genderLayout, 1, "Please enter your gender."));
        }
        this.gender = (CustomSpinnerTextView) genderLayout.findViewById(R.id.spinner_txt);
        this.gender.setUpSpinnerTextView(17367043, this.values);
        genderLayout.setHint("Gender*");
        TextInputLayout floatingFirstnameText = (TextInputLayout) findViewById(R.id.input_layout_firstname);
        if (!(floatingFirstnameText == null || floatingFirstnameText.getEditText() == null)) {
            floatingFirstnameText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingFirstnameText, 1, 100, "Please enter your first name.", "Your first name can have length up to 100 characters."));
        }
        TextInputLayout floatingLastnameText = (TextInputLayout) findViewById(R.id.input_layout_lastname);
        if (!(floatingLastnameText == null || floatingLastnameText.getEditText() == null)) {
            floatingLastnameText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingLastnameText, 1, 100, "Please enter your last name.", "Your last name can have length up to 100 characters."));
        }
        TextInputLayout floatingLasteverydayText = (TextInputLayout) findViewById(R.id.input_layout_everyday);
        if (!(floatingLasteverydayText == null || floatingLasteverydayText.getEditText() == null)) {
            floatingLasteverydayText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingLasteverydayText, 0, 150, null, "Your everyday title can have lengh up to 150 characters."));
        }
        this.idText.setText(this.volModel.bases.id.toString());
        this.firstName.setText(this.volModel.bases.firstName);
        this.lastName.setText(this.volModel.bases.lastName);
        this.everyDayTitle.setText(this.volModel.bases.everydayTitle);
        this.title.setText(this.volModel.bases.title);
        this.gender.setText(this.volModel.bases.gender);
        if (!TextUtils.isEmpty(this.volModel.bases.avatar)) {
            new DownloadImageTask(avatar, false, this)
                    .execute(new String[]{ImageHelper.getAvatarUrl(this.volModel.bases.avatar, getResources().getString(R.string.AVATAR_SIZE_EXTENSION))});
        }
    }

    protected void OnSaveOptionsItemSelected() {
        this.volModel.bases.firstName = this.firstName.getText().toString();
        this.volModel.bases.lastName = this.lastName.getText().toString();
        this.volModel.bases.everydayTitle = this.everyDayTitle.getText().toString();
        this.volModel.bases.title = this.title.getText().toString();
        this.volModel.bases.gender = this.gender.getText().toString();
        new putVolunteerbases(this.volModel.bases.toJsonObject().toString()).execute(new Void[0]);
    }

    @LayoutRes
    protected int getLayoutResID() {
        return R.layout.activity_basic_info;
    }

    public void onPickImage(View view) {
        startActivityForResult(getPickImageIntent(this), PICK_IMAGE_ID);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_IMAGE_ID /*234*/:
                this.avatarFile = getImageFromResult(this, resultCode, data);
                putUserNeedUpdateProfile(TextUtils.isEmpty(this.volModel.bases.avatar));
                return;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                return;
        }
    }

    class C08181 extends ArrayList<String> {
        C08181() {
            add("");
            add("Male");
            add("Female");
            add("X");
        }
    }

    public class putVolunteerAvatar extends ApiRestFullRequest {
        public putVolunteerAvatar() {
            super(HttpRequestType.POST, BasicInfoActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/avatarFile", BasicInfoActivity.this.avatarFile, BasicInfoActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            BasicInfoActivity.this.finishActivity();
        }
    }

    public class putVolunteerbases extends ApiRestFullRequest {
        public putVolunteerbases(String param) {
            super(HttpRequestType.PUT, BasicInfoActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer", param, BasicInfoActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            new putVolunteerAvatar().execute(new Void[0]);
        }
    }
}
