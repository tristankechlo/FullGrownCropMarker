package com.tristankechlo.crop_marker.commands;

import com.tristankechlo.crop_marker.FullGrownCropMarker;
import com.tristankechlo.crop_marker.config.ConfigManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

public final class ResponseHelper {

    public static void sendMessageConfigShow(FabricClientCommandSource source) {
        MutableComponent clickableFile = clickableConfig();
        MutableComponent message = new TextComponent("Config-file can be found here: ").append(clickableFile);
        sendMessage(source, message.withStyle(ChatFormatting.WHITE));
    }

    public static void sendMessageConfigReload(FabricClientCommandSource source) {
        MutableComponent message = new TextComponent("Config was successfully reloaded.");
        sendMessage(source, message.withStyle(ChatFormatting.WHITE));
        sendMessageReloadAssets(source);
    }

    public static void sendMessageConfigReset(FabricClientCommandSource source) {
        MutableComponent message = new TextComponent("Config was successfully set to default.");
        sendMessage(source, message.withStyle(ChatFormatting.WHITE));
        sendMessageReloadAssets(source);
    }

    private static void sendMessageReloadAssets(FabricClientCommandSource source) {
        MutableComponent message = new TextComponent("You need to reload the game assets for the changes to take effect.");
        sendMessage(source, message.withStyle(ChatFormatting.WHITE));
        MutableComponent message2 = new TextComponent("You can do this by pressing F3 + T or by restarting the game.");
        sendMessage(source, message2.withStyle(ChatFormatting.WHITE));
    }

    public static MutableComponent start() {
        return new TextComponent("[" + FullGrownCropMarker.MOD_NAME + "] ").withStyle(ChatFormatting.GOLD);
    }

    public static void sendMessage(FabricClientCommandSource source, Component message) {
        MutableComponent start = start().append(message);
        source.sendFeedback(start);
    }

    public static MutableComponent clickableConfig() {
        String fileName = ConfigManager.FILE_NAME;
        String filePath = ConfigManager.getConfigPath();
        MutableComponent mutableComponent = new TextComponent(fileName);
        mutableComponent.withStyle(ChatFormatting.GREEN, ChatFormatting.UNDERLINE);
        mutableComponent.withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, filePath)));
        return mutableComponent;
    }

    public static MutableComponent clickableLink(String url, String displayText) {
        MutableComponent mutableComponent = new TextComponent(displayText);
        mutableComponent.withStyle(ChatFormatting.GREEN, ChatFormatting.UNDERLINE);
        mutableComponent.withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)));
        return mutableComponent;
    }

    public static MutableComponent clickableLink(String url) {
        return clickableLink(url, url);
    }

}