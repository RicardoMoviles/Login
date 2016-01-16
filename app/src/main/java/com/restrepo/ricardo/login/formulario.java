package com.restrepo.ricardo.login;

        import android.app.Activity;
        import android.app.Fragment;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.view.View.OnClickListener;
        import android.widget.EditText;



/**
 * A simple {@link Fragment} subclass.
 */
public class formulario extends Fragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FormularioListener) {
            listener = (FormularioListener) activity;
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_formulario, container, false);
        ((Button) vista.findViewById(R.id.aceptar))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        pulsadoBoton(v);
                    }
                });
        ((Button) vista.findViewById(R.id.cancelar))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        pulsadoBoton(v);
                    }
                });

        return vista;
    }

    public interface FormularioListener {
        public void pulsado(int resultado,String control, String log,String correo,String contraseña);
    }

    public void pulsadoBoton(View v) {
       String log=((EditText) getActivity().findViewById(R.id.eUsuario)).getText().toString();
       String mail=((EditText) getActivity().findViewById(R.id.eCorreo)).getText().toString();
       String pwd1=((EditText) getActivity().findViewById(R.id.eContraseña)).getText().toString();
       String pwd2=((EditText) getActivity().findViewById(R.id.eRepetir)).getText().toString();


        if (null == listener) {
            return;
        }
        if (((Button) v).getText().equals("Ok")) {
            if(pwd1.equals(pwd2)) {
                listener.pulsado(OK,"Enviado",log,mail,pwd1);
            }else{
                listener.pulsado(Error,"Contraseña Incorrecta","","","");
            }

        } else{
            listener.pulsado(CANCEL, "Nombre de Usuario","","","");
        }

    }

    public final static int OK = 0;
    public final static int CANCEL = 1;
    public final static int Error = 2;
    private FormularioListener listener;
}
