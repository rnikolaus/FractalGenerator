package fractal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import junit.framework.TestCase;

/**
 *
 * @author rapnik
 */
public class DimensionCreatorTest extends TestCase {
    
    public DimensionCreatorTest(String testName) {
        super(testName);
    }

    public void testNormal() {
        
        Collection<DimXY> dimensions = DimensionCreator.getDimensions(5, 5);
        try5x5dimensions(dimensions);
        
    }
    public void testProducer(){
        ArrayList<DimXY> result = new ArrayList<>();
        Iterator<DimXY> it = DimensionCreator.getDimensionProducer(5, 5);
        while (it.hasNext()){
            result.add(it.next());
        }
        try5x5dimensions(result);
    }

    private void try5x5dimensions(Collection<DimXY> dimensions) {
        assertEquals(5*5, dimensions.size());
        assertTrue(dimensions.contains(new DimXY(0, 0)));
        assertTrue(dimensions.contains(new DimXY(4, 0)));
        assertTrue(dimensions.contains(new DimXY(0, 4)));
        assertTrue(dimensions.contains(new DimXY(4, 4)));
    }
    
}
