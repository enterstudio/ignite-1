/* @java.file.header */

/*  _________        _____ __________________        _____
 *  __  ____/___________(_)______  /__  ____/______ ____(_)_______
 *  _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
 *  / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
 *  \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
 */

package org.gridgain.grid.kernal.processors.interop;

import org.apache.ignite.*;
import org.gridgain.grid.kernal.*;

/**
 * Interface for interop-aware components.
 */
public interface GridInteropAware {
    /**
     * Initializes interop-aware component.
     *
     * @param ctx Context.
     * @throws IgniteCheckedException In case of error.
     */
    public void initialize(GridKernalContext ctx) throws IgniteCheckedException;

    /**
     * Destroys interop-aware component.
     *
     * @param ctx Context.
     * @throws IgniteCheckedException In case of error.
     */
    public void destroy(GridKernalContext ctx) throws IgniteCheckedException;
}
