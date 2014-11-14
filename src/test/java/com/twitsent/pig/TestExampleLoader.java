package com.twitsent.pig;

import junit.framework.Assert;
import org.apache.commons.lang.StringUtils;
import org.apache.pig.ExecType;
import org.apache.pig.data.Tuple;
import org.apache.pig.pigunit.pig.PigServer;
import org.apache.pig.test.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class TestExampleLoader {

    static PigServer pig;

    private static final String dataDir = "build/test/tmpdata/";
    private static final String input1 = "template_loader_push_input1";

    @Before
    public void setup() throws IOException {
        pig = new PigServer(ExecType.LOCAL);

        Util.deleteDirectory(new File(dataDir));
        try {
            pig.mkdirs(dataDir);

            Util.createLocalInputFile(dataDir + input1,
                    new String[] {
                            "test_data\there",
                            "second_record\there"
                    });

        }
        catch (IOException e) {};
    }

    @After
    public void cleanup() throws IOException {
        Util.deleteDirectory(new File(dataDir));
        pig.shutdown();
    }

    @Test
    public void testLoader() throws IOException {
        pig.registerQuery(
                "data = load '" + dataDir + input1 + "' " +
                        "using com.twitsent.pig.ExampleLoader() AS (f1: chararray, f2: chararray);"
        );

        Iterator<Tuple> data = pig.openIterator("data");
        String[] expected = {
                "(test_data,here)",
                "(second_record,here)"
        };
        Assert.assertEquals(StringUtils.join(expected, "\n"), StringUtils.join(data, "\n"));
    }


}
