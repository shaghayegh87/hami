package ir.makapps.hami.screens.main.fragments.addAdvertise.mvp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolLongClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import de.mrapp.android.dialog.MaterialDialog;
import dmax.dialog.SpotsDialog;
import ir.makapps.hami.MapActivity;
import ir.makapps.hami.R;
import ir.makapps.hami.di.App;
import ir.makapps.hami.model.CityModel;
import ir.makapps.hami.model.ImageModel;
import ir.makapps.hami.screens.base.BaseFragment;
import ir.makapps.hami.screens.detail.mvp.DetailActivity;
import ir.makapps.hami.screens.main.fragments.addAdvertise.dagger.AddModule;
import ir.makapps.hami.screens.main.fragments.addAdvertise.dagger.DaggerAddComponent;
import ir.makapps.hami.utils.Utils;
import ir.map.sdk_map.MapirStyle;
import ir.map.sdk_map.maps.MapView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_CANCELED;
import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ROTATION_ALIGNMENT_VIEWPORT;

public class AddAdvertiseFragment extends BaseFragment implements AddAdvertiseFragmentContract.View, View.OnClickListener,
        CityAdapter.callBackInterface, AddPictureAdapter.callBackInterface {


    @Inject
    AddAdvertiseFragmentContract.Presenter presenter;
    private View customView, view;
    private MaterialDialog dialog;
    private Button btn_insert;
    private ProgressBar progressBar;
    private List<MultipartBody.Part> filePart;
    private MultipartBody.Part fileBody;
    private RecyclerView recyclerView, recycler_city;
    private ConstraintLayout select_image, constraint_recycler;
    private AddPictureAdapter adapter;
    private int numberOfColumns = 3;
    private String picturePath;
    private EditText edtSelectCity, edtTitle, edtDescription, edtAddress;
    private Button btn_map;
    String Lat, Lng;
    List<ImageModel> imageList = new ArrayList<>();
    private Uri pictureUri;
    private int imageListSize;
    private File image;
    private int position;
    private static final int Camera_permission = 2040;
    private static final int Gallery_permission = 2050;
    private int cityId;
    private Bundle bundle;
    private double latitude;
    private double longitude;
    private LatLng locationPoint, comeFromEditingAddressPosition;
    private MapView mapView;
    private Style mapStyle;
    private MapboxMap map;
    private ConstraintLayout constraintMap;
    private AlertDialog alertDialog;

    @Override
    public void injectDagger() {
        DaggerAddComponent.builder().
                appComponent(App.getAppComponent()).
                addModule(new AddModule(this)).
                build().injectToAddFragment(this);
    }

//    public void setLatLng(String lat, String lng) {
//        this.Lat = lat;
//        this.Lng = lng;
//
//    }

    @Override
    public void getData() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bundle = savedInstanceState;
        filePart = new ArrayList<>();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_add;
    }


    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
        checkLatLong();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void bindViews() {
        select_image = rootView.findViewById(R.id.select_image);
        constraintMap = rootView.findViewById(R.id.constraint_add_mapview);
        constraintMap.setVisibility(View.GONE);
        mapView = rootView.findViewById(R.id.map_view_add);
        recyclerView = rootView.findViewById(R.id.recycler);
        btn_insert = rootView.findViewById(R.id.btn_insert);
        btn_map = rootView.findViewById(R.id.btn_add_map);
        constraint_recycler = rootView.findViewById(R.id.constraint_recycler);
        edtSelectCity = rootView.findViewById(R.id.edt_select_city);
        edtSelectCity.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.showSoftInput(edtSelectCity, InputMethodManager.SHOW_IMPLICIT);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        view = getActivity().findViewById(android.R.id.content);
        customView = getLayoutInflater().inflate(R.layout.dialog_city, null);
        edtTitle = rootView.findViewById(R.id.edit_title);
        edtDescription = rootView.findViewById(R.id.edit_description);
        edtAddress = rootView.findViewById(R.id.edit_address);
        recycler_city = customView.findViewById(R.id.recycler_dialog);
        select_image.setOnClickListener(this);
        edtSelectCity.setOnClickListener(this);
        btn_insert.setOnClickListener(this);
        btn_map.setOnClickListener(this);

    }

    @Override
    public void showMessage(String msg, String color) {
        Utils.showSnackbar(view, msg, color);
    }

    public void showDialog() {
        new MaterialDialog.Builder(getContext())
                .setItems(new CharSequence[]{Utils.camera, Utils.gallery, Utils.close}, pictureDialogListener)
                .create()
                .show();
    }

    public void displayCamera() {
        File imagesFolder = new File(Environment
                .getExternalStorageDirectory(), getContext().getResources()
                .getString(R.string.app_package));
        try {
            if (!imagesFolder.exists()) {
                boolean isCreated = imagesFolder.mkdirs();
                if (!isCreated) {
                    Toast.makeText(getContext(), R.string.mapbox_offline_error_region_definition_invalid, Toast.LENGTH_LONG).show();//todo change string
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",  /* suffix */
                    storageDir     /* directory */
            );
            pictureUri = FileProvider.getUriForFile(getContext(), getContext().getPackageName(), image);

            picturePath = image.getAbsolutePath();//Store this path as globe variable

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
            startActivityForResult(intent, 2);

        } catch (ActivityNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermissionCamera() throws IOException {
        if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        if (imageList.size() < 3) {
                            constraint_recycler.setVisibility(View.VISIBLE);
                            displayCamera();
                        } else

                            Utils.showSnackbar(getView(), getContext().getResources().getString(R.string.enough_picture), "red");
                    }
                }
            }

        } else {
            String[] permissionsRequests = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permissionsRequests, Camera_permission);
        }

    }

    public void checkPermissionReadStorage() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            choosePhotoFromGallery();
        } else {
            String[] permissionsRequests = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permissionsRequests, Gallery_permission);
        }
    }

    public void addImageToList(Uri uri, File image) {
//        picturePath = uri.getPath();
//        pictureUri = Uri.fromFile(new File(picturePath));
        imageListSize = imageList.size();
        if (imageListSize < 3) {
            constraint_recycler.setVisibility(View.VISIBLE);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                bitmap = cropAndScale(bitmap, 300);
                ImageModel imageModel = new ImageModel();
                imageModel.setName(image.getName());
                imageModel.setId(imageListSize + 1);
                imageModel.setBitmap(bitmap);
                imageModel.setFile(image);
                imageModel.setLocal_Image_Uri(pictureUri);
                imageModel.setPath(picturePath);
                imageList.add(imageModel);
                adapter = new AddPictureAdapter(getContext(), imageList, this);
                constraint_recycler.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(adapter);
                image = null;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else
            Utils.showSnackbar(view, getContext().getResources().getString(R.string.enough_picture), "red");

    }


    public static Bitmap cropAndScale(Bitmap source, int scale) {
        int factor = source.getHeight() <= source.getWidth() ? source.getHeight() : source.getWidth();
        int longer = source.getHeight() >= source.getWidth() ? source.getHeight() : source.getWidth();
        int x = source.getHeight() >= source.getWidth() ? 0 : (longer - factor) / 2;
        int y = source.getHeight() <= source.getWidth() ? 0 : (longer - factor) / 2;
        source = Bitmap.createBitmap(source, x, y, factor, factor);
        source = Bitmap.createScaledBitmap(source, scale, scale, false);
        return source;
    }

    public File reduceSizeOfFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 3;
            // factor of downsizing the image
            FileInputStream inputStream = new FileInputStream(file);
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();
            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    DialogInterface.OnClickListener pictureDialogListener = new DialogInterface.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    try {
                        checkPermissionCamera();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    checkPermissionReadStorage();
                    break;
                case 2:

                    break;
            }
        }
    };

    DialogInterface.OnClickListener EditPictureDialogListener = new DialogInterface.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
//                    ImageModel model = imageList.get(position);
                    for (ImageModel model : imageList) {
                        if (model == imageList.get(position)) {

                            try {
                                Bitmap bitmap = checkPictureForRotate(model.getPath(), model.getBitmap());
                                imageList.get(position).setBitmap(bitmap);
                                model.setFile(saveBitmap(model.getBitmap(), model.getPath()));
                                adapter.notifyDataSetChanged();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    break;
                case 1:
                    imageList.remove(position);
                    adapter.notifyItemRemoved(position);
                    break;
                case 2:

                    break;
            }
        }
    };


    public Bitmap checkPictureForRotate(String photoPath, Bitmap bitmap) throws IOException {
        ExifInterface ei = new ExifInterface(photoPath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch (orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
                rotatedBitmap = bitmap;
                break;

            default:
                rotatedBitmap = bitmap;
                Utils.showSnackbar(view, "زاویه عکس شما مشکلی برای ویرایش ندارد.", "red");
        }
        return rotatedBitmap;
    }

    private File saveBitmap(Bitmap bitmap, String path) {
        File file = null;
        if (bitmap != null) {
            file = new File(path);
            try {
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(path); //here is set your file path where you want to save or also here you can set file object directly

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream); // bitmap is your Bitmap instance, if you want to compress it you can compress reduce percentage
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 2:
                    addImageToList(pictureUri, image);
                    break;

                case 100:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    if (selectedImage != null) {
                        getPathWithCursor(selectedImage, filePathColumn);
                    }
                    break;

            }
        }
    }

    public void getPathWithCursor(Uri selectedImage, String[] filePathColumn) {
        Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            pictureUri = Uri.fromFile(new File(picturePath));
            image = new File(pictureUri.getPath());
            addImageToList(pictureUri, image);

        }
    }


    @Override
    public void showDialogEditPicture(int position) {
        this.position = position;
        Log.d("showshow", "showDialogEditPicture: " + position + "");
        new MaterialDialog.Builder(getContext())
                .setItems(new CharSequence[]{Utils.Rotate, Utils.Delete, Utils.close}, EditPictureDialogListener)
                .create()
                .show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_insert:
                addAdvertise();
                break;

            case R.id.btn_add_map:
                startActivity(new Intent(getActivity(), MapActivity.class));
                break;


            case R.id.edt_select_city:
                String token = Utils.getToken();
                presenter.getStates(token);
                break;

            case R.id.select_image:
                showDialog();
                break;
        }
    }

    private void addAdvertise() {
        if (notEmptyFiled()) {
            for (ImageModel model : imageList) {
                if (imageList.size() > filePart.size()) {
                    File file = reduceSizeOfFile(model.getFile());
                    RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                    fileBody = MultipartBody.Part.createFormData("files", file.getName(), mFile);
                    filePart.add(fileBody);
                }
            }
            Lat = Utils.readFromHawk("lat", "");
            Lng = Utils.readFromHawk("lan", "");
            presenter.insertAdvertise(filePart, edtTitle.getText().toString(), edtAddress.getText().toString(), edtDescription.getText().toString(), cityId, Lat, Lng);
        } else
            Utils.showSnackbar(view, getContext().getResources().getString(R.string.empty_fields), "red");
    }

    private boolean notEmptyFiled() {
        Boolean result = false;
        if (getTitleValue() && getCityValue() && getDescriptionValue() && getAddressValue()) {
            result = true;
        }
        return result;
    }

    private boolean getTitleValue() {
        if (edtTitle.getText().toString().isEmpty()) {
            edtTitle.setError(getContext().getResources().getString(R.string.empty_title));
            return false;
        } else return true;

    }

    private boolean getDescriptionValue() {
        if (edtDescription.getText().toString().isEmpty()) {
            edtDescription.setError(getContext().getResources().getString(R.string.empty_description));
            return false;
        } else return true;
    }

    private boolean getAddressValue() {
        if (edtAddress.getText().toString().isEmpty()) {
            edtAddress.setError(getContext().getResources().getString(R.string.empty_address));
            return false;
        } else return true;
    }

    private boolean getCityValue() {

        if (edtSelectCity.getText().toString().isEmpty()) {
            edtSelectCity.setError(getContext().getResources().getString(R.string.empty_city));
            return false;
        } else return true;
    }


    public void choosePhotoFromGallery() {
        if (imageList.size() < 3) {
            constraint_recycler.setVisibility(View.VISIBLE);
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, 100);
        } else
            Utils.showSnackbar(getView(), getContext().getResources().getString(R.string.enough_picture), "red");
    }


    @Override
    public void clearInformationAdvertise() {
        edtTitle.setText("");
        edtAddress.setText("");
        edtDescription.setText("");
        imageList.clear();
        edtSelectCity.setText("");
//        recyclerView.setVisibility(View.GONE);
        filePart.clear();
        constraintMap.setVisibility(View.GONE);
        constraint_recycler.setVisibility(View.GONE);
        Utils.insertToHawk("lat", "");
        Utils.insertToHawk("lan", "");
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }


    @Override
    public void showCityList(List<CityModel> cityList) {
        MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(getContext());
        dialogBuilder
                .setView(customView)
                .setMargin(70, 70, 70, 70)
                .setNegativeButton("بستن", null);
        dialog = dialogBuilder.create();
        recycler_city.setAdapter(new CityAdapter(cityList, getContext(), dialog, this));
        dialog.show();
    }

    public void setCityName(String name, int id) {
        cityId = 0;
        edtSelectCity.setText(name);
        cityId = id;
    }


    private void checkLatLong() {
        String lat = Utils.readFromHawk("lat", "");
        String lan = Utils.readFromHawk("lan", "");
        if (!lat.equals("") && !lan.equals("")) {
            latitude = Double.parseDouble(lat);
            longitude = Double.parseDouble(lan);
            locationPoint = new LatLng(latitude, longitude);
            Log.d("sssdffg", latitude + "  " + longitude + "");
            showLocation();
            constraintMap.setVisibility(View.VISIBLE);
        } else constraintMap.setVisibility(View.GONE);
    }

    private void showLocation() {
        mapView.onCreate(bundle);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                map = mapboxMap;
                manageCameraPosition();
                map.setStyle(new Style.Builder().fromUri(MapirStyle.MAIN_MOBILE_VECTOR_STYLE), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        mapStyle = style;
                        addSymbolToMap();
                        reverseGeocode(mapboxMap.getCameraPosition());
                        mapboxMap.addOnCameraIdleListener(new MapboxMap.OnCameraIdleListener() {
                            @Override
                            public void onCameraIdle() {

                                reverseGeocode(mapboxMap.getCameraPosition());
                            }
                        });

                    }
                });
                mapView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "mapview", Toast.LENGTH_SHORT).show();
                    }
                });


                mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public boolean onMapClick(@NonNull LatLng point) {
                        return false;
                    }
                });


            }


        });
    }

    private void addSymbolToMap() {
        mapStyle.addImage("sample_image_id", getResources().getDrawable(R.drawable.ic_pin));
        SymbolManager sampleSymbolManager = new SymbolManager(mapView, map, mapStyle);
        sampleSymbolManager.addClickListener(new OnSymbolClickListener() {
            @Override
            public void onAnnotationClick(Symbol symbol) {
                mapView = view.findViewById(R.id.map_view_detail);
            }
        });
        sampleSymbolManager.addLongClickListener(new OnSymbolLongClickListener() {
            @Override
            public void onAnnotationLongClick(Symbol symbol) {

//                Toast.makeText(DetailActivity.this, "This is LONG_CLICK_EVENT", Toast.LENGTH_SHORT).show();
            }
        });
// set non-data-driven properties, such as:
        sampleSymbolManager.setIconAllowOverlap(true);
        sampleSymbolManager.setIconRotationAlignment(ICON_ROTATION_ALIGNMENT_VIEWPORT);
// Add symbol at specified lat/lon
        SymbolOptions sampleSymbolOptions = new SymbolOptions();
        sampleSymbolOptions.withLatLng(locationPoint);
        sampleSymbolOptions.withIconImage("sample_image_id");
        sampleSymbolOptions.withIconSize(1.0f);
// save created Symbol Object for later access
        Symbol sampleSymbol = sampleSymbolManager.create(sampleSymbolOptions);
    }

    private void manageCameraPosition() {

        if (comeFromEditingAddressPosition != null) {

            setCameraInMap(map, comeFromEditingAddressPosition);

        } else {
            setCameraInMap(map, locationPoint);
        }
    }

    private void setCameraInMap(MapboxMap map, LatLng latLng) {

        map.setCameraPosition(
                new CameraPosition.Builder()
                        .target(latLng)
                        .zoom(15)
                        .build());


    }

    private void reverseGeocode(CameraPosition cameraPosition) {
        locationPoint = cameraPosition.target;
    }


    @Override
    public void showProgressbar() {
        alertDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.Custom)
                .setMessage(R.string.send_data)
                .setCancelable(false)
                .build();
        alertDialog.show();
    }

    @Override
    public void hideProgressbar() {
        alertDialog.dismiss();
    }

    @Override
    public void showError(String error) {
        Utils.showSnackbar(view, error, "green");
    }


}
