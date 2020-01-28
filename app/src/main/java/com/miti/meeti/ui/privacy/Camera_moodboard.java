package com.miti.meeti.ui.privacy;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.miti.meeti.MainActivity;
import com.miti.meeti.R;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.try123;
import com.miti.meeti.mitiutil.uihelper.ImageSaver;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.FileCallback;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.controls.Mode;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Camera_moodboard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Camera_moodboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Camera_moodboard extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View v;
    private FrameLayout cameralayout;
    private CameraView camera;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageButton clickcamera;
    private ImageButton clickrev;
    private OnFragmentInteractionListener mListener;
    private boolean facing;
    public Camera_moodboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Camera_moodboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Camera_moodboard newInstance(String param1, String param2) {
        Camera_moodboard fragment = new Camera_moodboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_camera_moodboard, container, false);
        clickcamera=v.findViewById(R.id.moodboard_camera_button);
        clickrev=v.findViewById(R.id.moodboard_camera_reverse);
        clickcamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                camera.takePicture();
            }
        });
        cameralayout=v.findViewById(R.id.cameralayout);
        camera=v.findViewById(R.id.camera);
        facing=true;
        clickrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(facing){
                    camera.setFacing(Facing.FRONT);
                    facing=false;
                }else{
                    camera.setFacing(Facing.BACK);
                    facing=true;
                }

            }
        });
        camera.setMode(Mode.PICTURE); // for pictures
        camera.setLifecycleOwner(this);
        camera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(PictureResult result) {
                Log.e("Control", Environment.getExternalStorageDirectory().toString());
                final String tempfolder= MainActivity.RootFolder+"/"+"temp";
                try123.createifnot(tempfolder);
                final String filename= tempfolder+"/"+"MC-" + try123.randomAlphaNumeric(32)+".jpg";
                File file = new File(filename);
                result.toFile(file, new FileCallback() {
                    @Override
                    public void onFileReady(@Nullable File file) {
                        Mlog.e("In camera callback","Control",file.getAbsolutePath());
                        if(file!=null){
                            new BoardImageSaver().execute("Moodboards","Moodboard",filename);
                        }
                        Navigation.findNavController(v).navigateUp();
                    }
                });
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
