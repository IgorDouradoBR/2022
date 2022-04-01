import java.util.HashMap;
import java.util.Map;
public class TipoR {

    public static String converteBin4(int value, int length) {
        String binaryString = Integer.toBinaryString(value);
        StringBuilder leadingZeroes = new StringBuilder();
        for (int index = 0; index < length - binaryString.length(); index++) {
            leadingZeroes = leadingZeroes.append("0");
        }

        return leadingZeroes + binaryString;
    }

    public static int converteBinarioParaDecimal(String valorBinario) {
        int valor = 0;

        // soma ao valor final o dígito binário da posição * 2 elevado ao contador da
        // posição (começa em 0)
        for (int i = valorBinario.length(); i > 0; i--) {
            valor += Integer.parseInt(valorBinario.charAt(i - 1) + "") * Math.pow(2, (valorBinario.length() - i));
        }

        return valor;
    }

    public static String codificaR(String r) {

        String[] splitar = r.split(" ");
        String instrucao = splitar[0];
        String operadores = splitar[1];

        String[] cadaOperador = operadores.split(",");

        // Formatos
        String opcode = "0";
        String rs = "0";
        String rt = "0";
        String rd = "0";
        String shamt = "0";
        String funct = "0";

        // Valor Instruções em Binário
        String valorzero = "000000"; // operando zero
        String div = "011010"; // 26 - tem operando zero
        String jr = "01000"; // 8 - tem operando zero
        String nor = "100111"; // 39 - tem operando zero
        String sll = "000000"; // 0 - tem operando zero

        if (instrucao.contains("div")) {
            opcode = valorzero;
            funct = div;
        } else if (instrucao.contains("jr")) {
            opcode = valorzero;
            funct = jr;
        } else if (instrucao.contains("nor")) {
            opcode = valorzero;
            funct = nor;
        } else if (instrucao.contains("sll")) {
            opcode = valorzero;
            funct = sll;
        }

        // Valor Registradores em Binário
        Map<String,Integer> registradores = new HashMap<String,Integer>();

        registradores.put( "$0", 0);
        registradores.put( "$1", 1);
        registradores.put( "$2", 2);
        registradores.put( "$3", 3);
        registradores.put( "$4", 4);
        registradores.put( "$5", 5);
        registradores.put( "$6", 6);
        registradores.put( "$7", 7);
        registradores.put( "$8", 8);
        registradores.put( "$9", 9);
        registradores.put( "$10", 10);
        registradores.put( "$11", 11);
        registradores.put( "$12", 12);
        registradores.put( "$13", 13);
        registradores.put( "$14", 14);
        registradores.put( "$15", 15);
        registradores.put( "$16", 16);
        registradores.put( "$17", 17);
        registradores.put( "$18", 18);
        registradores.put( "$19", 19);
        registradores.put( "$20", 20);
        registradores.put( "$21", 21);
        registradores.put( "$22", 22);
        registradores.put( "$23", 23);
        registradores.put( "$24", 24);
        registradores.put( "$25", 25);
        registradores.put( "$26", 26);
        registradores.put( "$27", 27);
        registradores.put( "$28", 28);
        registradores.put( "$29", 29);
        registradores.put( "$30", 30);
        registradores.put( "$31", 31);
        

        
        String aux= "0x";
        r= opcode;
        if(instrucao.equals("div")){
            int reg1= registradores.get(cadaOperador[0]);
            int reg2= registradores.get(cadaOperador[1]);
            String reg1Bin= Converte.converteDecimalParaBinario(reg1);
            String reg2Bin= Converte.converteDecimalParaBinario(reg2);
            r= r.concat("0".concat(reg1Bin)).concat("0".concat(reg2Bin)).concat("0000000000").concat(funct);
            for(int i=0;i<r.length();i=i+4){
                String porPartes= r.substring(i, i+4);
                aux= aux.concat(Converte.converteBinarioParaHexadecimal(porPartes));
            }
        }
        else if(instrucao.equals("jr")){
            int reg1= registradores.get(cadaOperador[0]);
            String reg1Bin= Converte.converteDecimalParaBinario(reg1);
            r= r.concat(reg1Bin).concat("0000000000000000").concat(funct);
            for(int i=0;i<r.length();i=i+4){
                String porPartes= r.substring(i, i+4);
                aux= aux.concat(Converte.converteBinarioParaHexadecimal(porPartes));
            } 
        }
        else if(instrucao.equals("sll")){
            int reg1= registradores.get(cadaOperador[0]);
            String reg1Bin= Converte.converteDecimalParaBinario(reg1);
            int reg2= registradores.get(cadaOperador[1]);
            String reg2Bin= Converte.converteDecimalParaBinario(reg2);
            String reg3= Converte.converteHexadecimalParaBinario(cadaOperador[2]);
            reg3= reg3.substring(reg3.length()-5);
            r= r.concat("00000").concat("0".concat(reg2Bin)).concat("0".concat(reg1Bin)).concat(reg3).concat(funct);
            for(int i=0;i<r.length();i=i+4){
                String porPartes= r.substring(i, i+4);
                aux= aux.concat(Converte.converteBinarioParaHexadecimal(porPartes));
            } 
        }
        else if(instrucao.equals("nor")){
            int reg3= registradores.get(cadaOperador[0]);
            String reg3Bin= Converte.converteDecimalParaBinario(reg3);
            int reg1= registradores.get(cadaOperador[1]);
            String reg1Bin= Converte.converteDecimalParaBinario(reg1);
            int reg2= registradores.get(cadaOperador[2]);
            String reg2Bin= Converte.converteDecimalParaBinario(reg2);
            r= r.concat("0".concat(reg1Bin)).concat("0".concat(reg2Bin)).concat("0".concat(reg3Bin)).concat("00000").concat(funct);
            for(int i=0;i<r.length();i=i+4){
                String porPartes= r.substring(i, i+4);
                aux= aux.concat(Converte.converteBinarioParaHexadecimal(porPartes));
            } 
        }

        return aux;

    }

    public static String decodificaR(String r) {

        Map<String,Integer> hxcodigos = new HashMap<String,Integer>();

        hxcodigos.


        String hexa = r.substring(2); // retira 0x da String

        int dec1 = Integer.parseInt((hexa.substring(0, 1))); // pega o primeiro numero da string
        String bin1 = converteBin4(dec1, 4); // transformar em binário de 4 bits

        int dec2 = Integer.parseInt((hexa.substring(1, 2))); // pega o segundo numero da string
        String bin2 = converteBin4(dec2, 4); // transformar em binário de 4 bits

        int dec3 = Integer.parseInt((hexa.substring(2, 3))); // ...
        String bin3 = converteBin4(dec3, 4); // transformar em binário de 4 bits

        int dec4 = Integer.parseInt((hexa.substring(3, 4)));
        String bin4 = converteBin4(dec4, 4);

        int dec5 = Integer.parseInt((hexa.substring(4, 5)));
        String bin5 = converteBin4(dec5, 4);

        int dec6 = Integer.parseInt((hexa.substring(5, 6)));
        String bin6 = converteBin4(dec6, 4);

        int dec7 = Integer.parseInt((hexa.substring(6, 7)));
        String bin7 = converteBin4(dec7, 4);

        int dec8 = Integer.parseInt((hexa.substring(7)));
        String bin8 = converteBin4(dec8, 4);

        // Juntar os números bin de 4 bits em um único
        String codBin = bin1 + bin2 + bin3 + bin4 + bin5 + bin6 + bin7 + bin8;

        // Separar codBin conforme Tipo R
        String opcode = codBin.substring(0, 6);
        String rs = codBin.substring(6, 11);
        String funct = codBin.substring(26);

        String rt = "0";
        String rd = "0";
        String shamt = "0";
       
        String aux = "opcode";

        // Fazer a tradução do binário para string
        // RS
        switch (converteBinarioParaDecimal(rs)) {
            case 0:
                rs = "$zero";
                break;
            case 1:
                rs = "$at";
                break;
            case 2:
                rs = "$v0";
                break;
            case 3:
                rs = "$v1";
                break;
            case 4:
                rs = "$a0";
                break;
            case 5:
                rs = "$a1";
                break;
            case 6:
                rs = "$a2";
                break;
            case 7:
                rs = "$a3";
                break;
            case 8:
                rs = "$t0";
                break;
            case 9:
                rs = "$t1";
                break;
            case 10:
                rs = "$t2";
                break;
            case 11:
                rs = "$t3";
                break;
            case 12:
                rs = "$t4";
                break;
            case 13:
                rs = "$t5";
                break;
            case 14:
                rs = "$t6";
                break;
            case 15:
                rs = "$t7";
                break;
            case 16:
                rs = "$s0";
                break;
            case 17:
                rs = "$s1";
                break;
            case 18:
                rs = "$s2";
                break;
            case 19:
                rs = "$s3";
                break;
            case 20:
                rs = "$s4";
                break;
            case 21:
                rs = "$s5";
                break;
            case 22:
                rs = "$s6";
                break;
            case 23:
                rs = "$s7";
                break;
            case 24:
                rs = "$t8";
                break;
            case 25:
                rs = "$t9";
                break;
            case 26:
                rs = "$k0";
                break;
            case 27:
                rs = "$k1";
                break;
            case 28:
                rs = "$gp";
                break;
            case 29:
                rs = "$sp";
                break;
            case 30:
                rs = "$fp";
                break;
            case 31:
                rs = "$ra";
                break;

        }


        // opcode e funct
        switch (opcode) {
            case "000100":
                opcode = "beq";
                funct = "";
                break;
            case "000110":
                opcode = "blez";
                funct = "";
                break;
            case "000011":
                opcode = "jal";
                funct = "";
                break;
            case "010111":
                opcode = "lw";
                funct = "";
                break;
            case "001101":
                opcode = "ori";
                funct = "";
                break;
            case "001010":
                opcode = "slti";
                funct = "";
                break;
            case "000000":
                if (funct.equals("011010")) {
                    opcode = "div";
                    funct = "";

                    rt = codBin.substring(11, 16);
                    rd = codBin.substring(16, 26);

                } else if (funct.equals("001000")) {
                    opcode = "jr";
                    funct = "";

                    rt = codBin.substring(11, 27);
                    funct = codBin.substring(27);

                } else if (funct.equals("100111")) {
                    opcode = "nor";
                    funct = "";

                    rt = codBin.substring(11, 16);
                    rd = codBin.substring(16, 21);
                    shamt = codBin.substring(21, 26);

                    // Shamt

                    if (Integer.parseInt(shamt) > 0) {
                        shamt = Integer.toString(converteBinarioParaDecimal(shamt));
                    } else {
                        shamt = "";
                    }

                } else if (funct.equals("000000")) {
                    opcode = "sll";
                    funct = "";
                    rt = codBin.substring(11, 16);
                    rd = codBin.substring(16, 21);
                    shamt = codBin.substring(21, 26);

                    // Shamt

                    if (Integer.parseInt(shamt) > 0) {
                        shamt = Integer.toString(converteBinarioParaDecimal(shamt));
                    } else {
                        shamt = "";
                    }
                }

                    // RT
                switch (converteBinarioParaDecimal(rt)) {
                    case 0:
                        rt = "$zero";
                        break;
                    case 1:
                        rt = "$at";
                        break;
                    case 2:
                        rt = "$v0";
                        break;
                    case 3:
                        rt = "$v1";
                        break;
                    case 4:
                        rt = "$a0";
                        break;
                    case 5:
                        rt = "$a1";
                        break;
                    case 6:
                        rt = "$a2";
                        break;
                    case 7:
                        rt = "$a3";
                        break;
                    case 8:
                        rt = "$t0";
                        break;
                    case 9:
                        rt = "$t1";
                        break;
                    case 10:
                        rt = "$t2";
                        break;
                    case 11:
                        rt = "$t3";
                        break;
                    case 12:
                        rt = "$t4";
                        break;
                    case 13:
                        rt = "$t5";
                        break;
                    case 14:
                        rt = "$t6";
                        break;
                    case 15:
                        rt = "$t7";
                        break;
                    case 16:
                        rt = "$s0";
                        break;
                    case 17:
                        rt = "$s1";
                        break;
                    case 18:
                        rt = "$s2";
                        break;
                    case 19:
                        rt = "$s3";
                        break;
                    case 20:
                        rt = "$s4";
                        break;
                    case 21:
                        rt = "$s5";
                        break;
                    case 22:
                        rt = "$s6";
                        break;
                    case 23:
                        rt = "$s7";
                        break;
                    case 24:
                        rt = "$t8";
                        break;
                    case 25:
                        rt = "$t9";
                        break;
                    case 26:
                        rt = "$k0";
                        break;
                    case 27:
                        rt = "$k1";
                        break;
                    case 28:
                        rt = "$gp";
                        break;
                    case 29:
                        rt = "$sp";
                        break;
                    case 30:
                        rt = "$fp";
                        break;
                    case 31:
                        rt = "$ra";
                        break;

                }

                // RD
                switch (converteBinarioParaDecimal(rd)) {
                    case 0:
                        rd = "$zero";
                        break;
                    case 1:
                        rd = "$at";
                        break;
                    case 2:
                        rd = "$v0";
                        break;
                    case 3:
                        rd = "$v1";
                        break;
                    case 4:
                        rd = "$a0";
                        break;
                    case 5:
                        rd = "$a1";
                        break;
                    case 6:
                        rd = "$a2";
                        break;
                    case 7:
                        rd = "$a3";
                        break;
                    case 8:
                        rd = "$t0";
                        break;
                    case 9:
                        rd = "$t1";
                        break;
                    case 10:
                        rd = "$t2";
                        break;
                    case 11:
                        rd = "$t3";
                        break;
                    case 12:
                        rd = "$t4";
                        break;
                    case 13:
                        rd = "$t5";
                        break;
                    case 14:
                        rd = "$t6";
                        break;
                    case 15:
                        rd = "$t7";
                        break;
                    case 16:
                        rd = "$s0";
                        break;
                    case 17:
                        rd = "$s1";
                        break;
                    case 18:
                        rd = "$s2";
                        break;
                    case 19:
                        rd = "$s3";
                        break;
                    case 20:
                        rd = "$s4";
                        break;
                    case 21:
                        rd = "$s5";
                        break;
                    case 22:
                        rd = "$s6";
                        break;
                    case 23:
                        rd = "$s7";
                        break;
                    case 24:
                        rd = "$t8";
                        break;
                    case 25:
                        rd = "$t9";
                        break;
                    case 26:
                        rd = "$k0";
                        break;
                    case 27:
                        rd = "$k1";
                        break;
                    case 28:
                        rd = "$gp";
                        break;
                    case 29:
                        rd = "$sp";
                        break;
                    case 30:
                        rd = "$fp";
                        break;
                    case 31:
                        rd = "$ra";
                        break;
                }

                break;
        }

        if(opcode.equals("div")){
            aux = opcode.concat(" ").concat(rs).concat(",").concat(" ").concat(rt);
        } else if(opcode.equals("jr")){
            aux = opcode.concat(" ").concat(rs);
        } else if(opcode.equals("nor")){
            aux = opcode.concat(" ").concat(rd).concat(",").concat(" ").concat(rs).concat(",").concat(" ").concat("rt");
        } else if(opcode.equals("sll")){
            aux = opcode.concat(" ").concat(rd).concat(",").concat(" ").concat(rt).concat(",").concat(" ").concat("shamt");
        }


        return aux;

    }

}
