/* @java.file.header */

/*  _________        _____ __________________        _____
 *  __  ____/___________(_)______  /__  ____/______ ____(_)_______
 *  _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
 *  / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
 *  \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
 */

package org.gridgain.grid.kernal.managers.failover;

import org.apache.ignite.*;
import org.apache.ignite.cluster.*;
import org.apache.ignite.compute.*;
import org.apache.ignite.spi.failover.*;
import org.gridgain.grid.kernal.*;
import org.gridgain.grid.kernal.managers.*;

import java.util.*;

/**
 * Grid failover spi manager.
 */
public class GridFailoverManager extends GridManagerAdapter<FailoverSpi> {
    /**
     * @param ctx Kernal context.
     */
    public GridFailoverManager(GridKernalContext ctx) {
        super(ctx, ctx.config().getFailoverSpi());
    }

    /** {@inheritDoc} */
    @Override public void start() throws IgniteCheckedException {
        startSpi();

        if (log.isDebugEnabled())
            log.debug(startInfo());
    }

    /** {@inheritDoc} */
    @Override public void stop(boolean cancel) throws IgniteCheckedException {
        stopSpi();

        if (log.isDebugEnabled())
            log.debug(stopInfo());
    }

    /**
     * @param taskSes Task session.
     * @param jobRes Job result.
     * @param top Collection of all top nodes that does not include the failed node.
     * @return New node to route this job to.
     */
    public ClusterNode failover(GridTaskSessionImpl taskSes, ComputeJobResult jobRes, List<ClusterNode> top) {
        return getSpi(taskSes.getFailoverSpi()).failover(new GridFailoverContextImpl(taskSes, jobRes,
            ctx.loadBalancing()), top);
    }
}
