
package fractal.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JProgressBar;
import javax.swing.Timer;

/**
 *
 * @author rapnik
 */
public class ProgressBar extends JProgressBar{
    private final Timer progressBarTimer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int value = getValue();
            if (value == 100) {
                value = 0;
            } else {
                value++;
            }
            setValue(value);
        }
    });

    /**
     *
     */
    public ProgressBar() {
        progressBarTimer.setRepeats(true);
    }

    /**
     * Enable or disable the ProgressBar
     * @param active
     */
    public void setActive(boolean active){
        setEnabled(active);
        if (active){
            progressBarTimer.start();
        }else{
            progressBarTimer.stop();
            setValue(0);
        }
    }
    public boolean getActive(){
        return progressBarTimer.isRunning();
    }
}
