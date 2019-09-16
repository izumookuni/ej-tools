package cc.domovoi.tools.test;

import cc.domovoi.tools.test.tree.TreeA;
import cc.domovoi.tools.test.tree.TreeB;
import cc.domovoi.tools.test.tree.TreeC;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TreeTest {

    Logger logger = LoggerFactory.getLogger(TreeTest.class);

    @Test
    public void testTree() throws Exception {

        TreeA tree1 = new TreeA("v1", Lists.newArrayList("1", "2"),
                new TreeB("v1-1", Lists.newArrayList(),
                        new TreeC("v1-1-1", Lists.newArrayList("3", "4")),
                        new TreeC("v1-1-2", Lists.newArrayList("1", "3")),
                        new TreeC("v1-1-3", Lists.newArrayList("2", "3"))),
                new TreeB("v1-2", Lists.newArrayList("2", "4")));

        TreeA tree2 = new TreeA("v1", Lists.newArrayList("2", "3"),
                new TreeB("v1-1", Lists.newArrayList("4"),
                        new TreeC("v1-1-1", Lists.newArrayList("3", "4")),
                        new TreeC("v1-1-2", Lists.newArrayList("1", "3"))),
                new TreeB("v1-2", Lists.newArrayList("2", "4"),
                        new TreeC("v1-2-1", Lists.newArrayList("1", "4"))));

//        tree1.flatMapTreeGeneral().forEach(relaxedTreeInterface -> logger.debug(relaxedTreeInterface.toString()));
//        logger.debug(tree1.maskTreeGeneral(tree2).toString());
//        logger.debug(tree1.mergeTreeGeneral(tree2).toString());
        tree2.iteratorGeneral().forEachRemaining(tree -> logger.debug(tree.toString()));

    }
}
