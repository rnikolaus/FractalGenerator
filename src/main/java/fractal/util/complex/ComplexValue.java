

package fractal.util.complex;

import java.math.BigDecimal;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.Precision;


/**
 *
 * @author rapnik
 */
public class ComplexValue {
    private final BigDecimal real;
    private final BigDecimal imaginary;

    public ComplexValue(Complex complex,int precision) {
        real = BigDecimal.valueOf(Precision.round(complex.getReal(), precision));
        imaginary = BigDecimal.valueOf(Precision.round(complex.getImaginary(), precision));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.real != null ? this.real.hashCode() : 0);
        hash = 97 * hash + (this.imaginary != null ? this.imaginary.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ComplexValue other = (ComplexValue) obj;
        if (this.real != other.real && (this.real == null || !this.real.equals(other.real))) {
            return false;
        }
        return this.imaginary == other.imaginary || (this.imaginary != null && this.imaginary.equals(other.imaginary));
    }
    
    public Complex getApacheComplex(){
        return new Complex(real.doubleValue(), imaginary.doubleValue());
    }

    @Override
    public String toString() {
        return "ComplexValue{" + "real=" + real + ", imaginary=" + imaginary + '}';
    }
    
    
}
