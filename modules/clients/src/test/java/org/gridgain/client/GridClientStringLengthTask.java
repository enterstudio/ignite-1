/* @java.file.header */

/*  _________        _____ __________________        _____
 *  __  ____/___________(_)______  /__  ____/______ ____(_)_______
 *  _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
 *  / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
 *  \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
 */

package org.gridgain.client;

import org.apache.ignite.*;
import org.apache.ignite.compute.*;

import java.util.*;

import static org.apache.ignite.compute.ComputeJobResultPolicy.*;

/**
 * Test task calculate length of the string passed in the argument.
 * <p>
 * The argument of the task is a simple string to calculate length of.
 */
public class GridClientStringLengthTask extends ComputeTaskSplitAdapter<String, Integer> {
    /** {@inheritDoc} */
    @Override protected Collection<? extends ComputeJob> split(int gridSize, String arg) throws IgniteCheckedException {
        Collection<ComputeJobAdapter> jobs = new ArrayList<>();

        if (arg != null)
            for (final Object val : arg.split(""))
                jobs.add(new ComputeJobAdapter() {
                    @Override public Object execute() {
                        try {
                            Thread.sleep(5);
                        }
                        catch (InterruptedException ignored) {
                            Thread.currentThread().interrupt();
                        }

                        return val == null ? 0 : val.toString().length();
                    }
                });

        return jobs;
    }

    /** {@inheritDoc} */
    @Override public Integer reduce(List<ComputeJobResult> results) throws IgniteCheckedException {
        int sum = 0;

        for (ComputeJobResult res : results)
            sum += res.<Integer>getData();

        return sum;
    }

    /** {@inheritDoc} */
    @Override public ComputeJobResultPolicy result(ComputeJobResult res, List<ComputeJobResult> rcvd)
        throws IgniteCheckedException {
        if (res.getException() != null)
            return FAILOVER;

        return WAIT;
    }
}
