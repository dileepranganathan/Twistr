package com.twitsent.pig;

import junit.framework.Assert;

import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.junit.Test;

import com.twitsent.pig.IdentityUDF;

import java.io.IOException;
import java.util.Arrays;

public class TestIdentityUDF {

    private TupleFactory tupleFactory = TupleFactory.getInstance();

    @Test
    public void testIdentityUDF() throws IOException {

        Tuple input = tupleFactory.newTuple(Arrays.asList("a", "b", "c"));
        IdentityUDF func = new IdentityUDF();
        Tuple output = func.exec(input);
        Assert.assertEquals(input, output);
    }

}
