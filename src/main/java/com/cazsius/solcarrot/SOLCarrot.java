package com.cazsius.solcarrot;

import com.cazsius.solcarrot.capability.FoodCapability;
import com.cazsius.solcarrot.capability.FoodStorage;
import com.cazsius.solcarrot.command.CommandClearFoodArray;
import com.cazsius.solcarrot.command.CommandSizeFoodArray;
import com.cazsius.solcarrot.common.CommonProxy;
import com.cazsius.solcarrot.handler.HandlerCapability;
import com.cazsius.solcarrot.handler.HandlerConfiguration;
import com.cazsius.solcarrot.handler.HandlerFoodTracker;
import com.cazsius.solcarrot.handler.HandlerTooltip;
import com.cazsius.solcarrot.handler.MaxHealthHandler;
import com.cazsius.solcarrot.lib.Constants;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER)
//TODO , dependencies = "required-after:AppleCore"
public class SOLCarrot {

	@Instance(Constants.MOD_ID)
	public static SOLCarrot instance;

	@SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
	private static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		HandlerConfiguration.initConfig(e.getSuggestedConfigurationFile());
		proxy.preInit(e);
	}

	@EventHandler
	public void Init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new HandlerFoodTracker());
		MinecraftForge.EVENT_BUS.register(new HandlerCapability());
		MinecraftForge.EVENT_BUS.register(MaxHealthHandler.class);
		CapabilityManager.INSTANCE.register(FoodCapability.class, new FoodStorage(), FoodCapability.class);
		MinecraftForge.EVENT_BUS.register(new HandlerTooltip());
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandClearFoodArray());
        event.registerServerCommand(new CommandSizeFoodArray());
    }
}