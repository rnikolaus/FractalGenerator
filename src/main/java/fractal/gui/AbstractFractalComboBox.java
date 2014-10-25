package fractal.gui;

import fractal.fractals.AbstractFractal;
import java.util.ServiceLoader;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author rapnik
 */
public class AbstractFractalComboBox extends JComboBox<AbstractFractal>{

    public AbstractFractalComboBox() {
        this.setModel(initComboBox());
    }
    
    private DefaultComboBoxModel<AbstractFractal> initComboBox(){
        DefaultComboBoxModel<AbstractFractal> comboBoxModel = new DefaultComboBoxModel<>();
        ServiceLoader<AbstractFractal> ls = ServiceLoader.load(AbstractFractal.class);
        for (AbstractFractal fs: ls){
            comboBoxModel.addElement(fs);
        }
        return comboBoxModel;
    }
}
