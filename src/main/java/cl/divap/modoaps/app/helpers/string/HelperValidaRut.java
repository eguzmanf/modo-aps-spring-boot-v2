package cl.divap.modoaps.app.helpers.string;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HelperValidaRut {

    public static boolean validarRut(String rutString) {
        //
        String rut = rutString.replace(".", "").replace(",", "").trim();

        Pattern pattern = Pattern.compile("^[0-9]+-[0-9kK]{1}$");
        Matcher matcher = pattern.matcher(rut);
        if ( matcher.matches() == false ) return false;
        String[] stringRut = rut.split("-");
        return stringRut[1].toLowerCase().equals(HelperValidaRut.dv(stringRut[0]));
    }

    public static String dv ( String rut ) {
        //
        Integer M=0,S=1,T=Integer.parseInt(rut);
        for (;T!=0;T=(int) Math.floor(T/=10))
            S=(S+T%10*(9-M++%6))%11;
        return ( S > 0 ) ? String.valueOf(S-1) : "k";
    }
}
