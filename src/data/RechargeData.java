package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.Recharge;
	
	public class RechargeData {
	    // Lista estática para almacenar las recargas.
	    public static ArrayList<Recharge> rechargeList = new ArrayList<>();
	    private static final String fileName = "student_recharges.json";
	    private static JsonUtils<Recharge> jsonUtils = new JsonUtils<>(fileName);

	    // Método para obtener la lista de recargas desde el archivo JSON.
	    public static List<Recharge> getRechargeList() {
	        try {
	            return jsonUtils.getElements(Recharge.class);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    // Método para guardar una recarga si es válida.
	    public static boolean saveRecharge(Recharge recharge) {
	        try {
	            jsonUtils.saveElement(recharge);
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

	    // Método para eliminar una recarga específica.
	    public static boolean deleteRecharge(Recharge recharge) {
	        try {
	            boolean isDeleted = jsonUtils.deleteElement(recharge);
	            return isDeleted;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

	    // Método para guardar la lista completa de recargas en el archivo JSON.
	    public static void saveRechargeListToFile(List<Recharge> rechargeList) throws IOException {
	        jsonUtils.saveElements(rechargeList);
	    }

	    // Método para actualizar una recarga en la lista y guardar los cambios en el archivo JSON.
	    public static boolean updateRecharge(Recharge updatedRecharge) {
	        try {
	            List<Recharge> rechargeList = getRechargeList();
	            for (int i = 0; i < rechargeList.size(); i++) {
	                if (rechargeList.get(i).getCarnet().equals(updatedRecharge.getCarnet())) {
	                    rechargeList.set(i, updatedRecharge);
	                    saveRechargeListToFile(rechargeList);
	                    return true;
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	    
	}
	
