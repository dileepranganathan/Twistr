package com.twitsent.pig;

import junit.framework.Assert;

import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.junit.Test;

import com.twitsent.pig.TwitSentUDF;

import java.io.IOException;
import java.util.Arrays;

public class TestTwitSentUDF {

    private TupleFactory tupleFactory = TupleFactory.getInstance();
    private BagFactory bagFactory = BagFactory.getInstance();


    @Test
    public void testTwitSentUDFIntegers() throws IOException {

        /*Tuple input = tupleFactory.newTuple(Arrays.asList(12, 3));
        TwitSentUDF func = new TwitSentUDF();
        DataBag output = func.exec(input);

        DataBag expected = bagFactory.newDefaultBag();

        expected.add(tupleFactory.newTuple(Arrays.asList(36.0)));
        expected.add(tupleFactory.newTuple(Arrays.asList(4.0)));

        Assert.assertEquals(expected, output);*/
    }
    
    @Test
    public void testIdentityUDF() throws IOException {

      /*  Tuple input = tupleFactory.newTuple(Arrays.asList("a", "b", "c"));
        TwitSentUDF func = new TwitSentUDF();
        DataBag output = (DataBag) func.exec(input);
        Assert.assertEquals(input, output);*/
    }

}
