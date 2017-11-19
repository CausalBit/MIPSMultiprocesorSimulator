/**
 * Created by J.A Rodríguez on 18/11/2017.
 */
public class GuiManager extends Thread {
    ConfigurationWindow cw;
    DataViewerWindow dw;
    ResultsWindow rw;

    public GuiManager(ConfigurationWindow cw, DataViewerWindow dw, ResultsWindow rw){
        this.cw = cw;
        this.dw = dw;
        this.rw = rw;
    }

    public void run(){
        cw.setLocationRelativeTo(null);
        cw.setVisible(true);
    }
}
