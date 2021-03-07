package ejercicio.app_m.controller;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import ejercicio.app_m.activity.InitialActivity;
import ejercicio.app_m.activity.ItemSpecsActivity;
import ejercicio.app_m.activity.ResultsActivity;
import ejercicio.app_m.Item.Item;

public class ControllerSpecs implements Controller {

    private ControllerSpecs(){}

    public static ControllerSpecs getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new ControllerSpecs();
        }

        return sSoleInstance;
    }

    private static ControllerSpecs sSoleInstance;
    private InitialActivity initialActivity;
    private ControllerInitial controllerInitial = ControllerInitial.getInstance();
    private ControllerResults controllerResults = ControllerResults.getInstance();
    private static ResultsActivity resultsActivity;
    private String query;
    private Item item;
    private ItemSpecsActivity itemSpecsActivity;


    @Override
    public void loadResultsActivity() {

    }


    public void initialize(String _query, ItemSpecsActivity _itemSpecsActivity){
        query = _query;
        itemSpecsActivity = _itemSpecsActivity;

    }

    public void startItemSpecsActivity(Item _item) {
        item = _item;
        controllerResults.startItemSpecsActivity(_item);

    }

    public void back(String _query) {
        controllerInitial.loadItems(_query);
    }

    public void onMoreInfo(Item _item) {

        String urlString = _item.getPermalink();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            controllerResults.getResultActivity().startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Si Chrome no esta instalado dejo al usuario elegir con que abrir el link
            intent.setPackage(null);
            initialActivity.startActivity(intent);
        }
    }

    public void onNewQuery(String _newQuery ) {
        if(!_newQuery.isEmpty()){
            controllerInitial.loadItems(_newQuery);
        }else{
           Toast.makeText(itemSpecsActivity, "Por favor ingrese un texto para buscar", Toast.LENGTH_SHORT).show();
        }
    }
}
