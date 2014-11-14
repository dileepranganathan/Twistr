package com.twitsent.pig;

import junit.framework.Assert;

import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.junit.Test;

import com.twitsent.pig.BagTupleExampleUDF;

import java.io.IOException;
import java.util.Arrays;

public class TestBagTupleExampleUDF {

    private TupleFactory tupleFactory = TupleFactory.getInstance();
    private BagFactory bagFactory = BagFactory.getInstance();


    @Test
    public void testBagTupleExampleUDFIntegers() throws IOException {

        Tuple input = tupleFactory.newTuple(Arrays.asList(12, 3));
        BagTupleExampleUDF func = new BagTupleExampleUDF();
        DataBag output = func.exec(input);

        DataBag expected = bagFactory.newDefaultBag();

        expected.add(tupleFactory.newTuple(Arrays.asList(36.0)));
        expected.add(tupleFactory.newTuple(Arrays.asList(4.0)));

        Assert.assertEquals(expected, output);
    }


    @Test
    public void testBagTupleExampleUDFDoubles() throws IOException {

        Tuple input = tupleFactory.newTuple(Arrays.asList(12.4, 4));
        BagTupleExampleUDF func = new BagTupleExampleUDF();
        DataBag output = func.exec(input);

        DataBag expected = bagFactory.newDefaultBag();

        expected.add(tupleFactory.newTuple(Arrays.asList(49.6)));
        expected.add(tupleFactory.newTuple(Arrays.asList(3.1)));

        Assert.assertEquals(expected, output);
    }


}
