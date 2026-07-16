/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.listener;

import com.prismenchants.PrismEnchants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockPlaceListener
implements Listener {

    @EventHandler(ignoreCancelled=true)
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        blockPlaceEvent.getBlock().setMetadata("pe_placed", new FixedMetadataValue(PrismEnchants.get(), true));
    }
}
