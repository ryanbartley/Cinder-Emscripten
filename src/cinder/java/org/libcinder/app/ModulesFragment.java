/*

Using ModulesFragment in Activity

1) Create a private class member:
    private ModulesFragment mModulesFragment;

2) Activity's onCreate should look something like this:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(ModulesFragment.FRAGMENT_TAG);
        if(null == fragment) {
            mModulesFragment = new ModulesFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(mModulesFragment, ModulesFragment.FRAGMENT_TAG);
            fragmentTransaction.commit();
        }
        else {
            mModulesFragment = (ModulesFragment)fragment;
        }
    }

*/
package org.libcinder.app;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import org.libcinder.hardware.Camera;

/** \class ModulesFragment
 *
 *
 */
public class ModulesFragment extends Fragment {

    private static final String TAG = "ModulesFragment";
    public static final String FRAGMENT_TAG = "fragment:org.libcinder.app.ModulesFragment";

    private static ModulesFragment sInstance;

    private static void set(ModulesFragment thisObj) {
        ModulesFragment.sInstance = thisObj;
    }

    public static ModulesFragment get() {
        if(null == sInstance) {
            Log.e(TAG, "ModulesFragment.sInstance is null (may not be allocated)");
        }
        return sInstance;
    }

    public static Activity activity() {
        return ModulesFragment.get().getActivity();
    }

    /** onAttach
     *
     */
    @Override
    public void onAttach(Activity activity) {
        Log.i(TAG, "onAttach");
        super.onAttach(activity);
        ModulesFragment.set(this);
    }

    /** onCreate
     *
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        this.checkCameraPresence();
    }

    /** onActivityCreated
     *
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    /** onStart
     *
     */
    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
    }

    /** onResume
     *
     */
    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    /** onPause
     *
     */
    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    /** onStop
     *
     */
    @Override
    public void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    /** onDestroy
     *
     */
    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    /** onDetach
     *
     */
    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach");
        super.onDestroy();
    }

    // =============================================================================================
    // Permission
    // =============================================================================================

    /** \class Permissions
     *
     */
    public class Permissions {

        /** \class Missing
         *
         */
        public class Missing {

            private String msg(String ident) {
                return "Permission denied (maybe missing " + ident + " permission)";
            }

            public String CAMERA()                  { return msg(Manifest.permission.CAMERA); }
            public String INTERNET()                { return msg(Manifest.permission.INTERNET); }
            public String WRITE_EXTERNAL_STORAGE()  { return msg(Manifest.permission.WRITE_EXTERNAL_STORAGE); }
        }

        private Missing mMissing = new Missing();

        public Missing missing() {
            return mMissing;
        }

        private boolean check(String ident) {
            return (getActivity().checkCallingOrSelfPermission(ident) == PackageManager.PERMISSION_GRANTED);
        }

        public boolean CAMERA()                 { return check(Manifest.permission.CAMERA); }
        public boolean INTERNET()               { return check(Manifest.permission.INTERNET); }
        public boolean WRITE_EXTERNAL_STORAGE() { return check(Manifest.permission.WRITE_EXTERNAL_STORAGE); }

    }

    private Permissions mPermssions = new Permissions();

    public static Permissions permissions() {
        return ModulesFragment.get().mPermssions;
    }

    // =============================================================================================
    // Display
    // =============================================================================================

    public Display getDefaultDisplay() {
        Display result = null;
        if(null != getActivity()) {
            WindowManager wm = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
            result = wm.getDefaultDisplay();
        }
        return result;
    }

    public Point getDefaultDisplaySize() {
        Display defaultDisplay = this.getDefaultDisplay();
        Point result = new Point();
        defaultDisplay.getRealSize(result);
        return result;
    }

    // =============================================================================================
    // Camera
    // =============================================================================================

    private Camera mCamera;
    private boolean mBackCameraAvailable = false;
    private boolean mFrontCameraAvailable = false;

    private void checkCameraPresence() {
        mBackCameraAvailable = false;
        mFrontCameraAvailable = false;

        boolean[] back = { false };
        boolean[] front = { false };
        Camera.checkCameraPresence(back, front);

        mBackCameraAvailable = back[0];
        mFrontCameraAvailable = front[0];

        Log.i(TAG, "Back camera present : " + mBackCameraAvailable);
        Log.i(TAG, "Front camera present : " + mFrontCameraAvailable);
    }

    public boolean isBackCameraAvailable() {
        return mBackCameraAvailable;
    }

    public boolean isFrontCameraAvailable() {
        return mFrontCameraAvailable;
    }

    public Camera getCamera() {
        if(null == mCamera) {
            mCamera = Camera.create();
            addCameraFragment(mCamera);
        }
        return mCamera;
    }

    public Camera getCamera(int version) {
        if(null == mCamera) {
            mCamera = Camera.create(version);
            addCameraFragment(mCamera);
        }
        return mCamera;
    }

    private void addCameraFragment(Camera camera) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(Camera.FRAGMENT_TAG);
        if(null == fragment) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(camera, Camera.FRAGMENT_TAG);
            fragmentTransaction.commit();
        }
    }
}