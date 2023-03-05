package com.tristankechlo.crop_marker.commands;

import com.tristankechlo.crop_marker.FullGrownCropMarker;
import com.tristankechlo.crop_marker.config.ConfigManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class ResponseHelper {

    public static void sendMessageConfigShow(CommandSourceStack source) {
        MutableComponent clickableFile = clickableConfig();
        MutableComponent message = Component.literal("Config-file can be found here: ").append(clickableFile);
        sendMessage(source, message.withStyle(ChatFormatting.WHITE));
    }

    public static void sendMessageConfigReload(CommandSourceStack source) {
        MutableComponent message = Component.literal("Config was successfully reloaded.");
        sendMessage(source, message.withStyle(ChatFormatting.WHITE));
        sendMessageReloadAssets(source);
    }

    public static void sendMessageConfigReset(CommandSourceStack source) {
        MutableComponent message = Component.literal("Config was successfully set to default.");
        sendMessage(source, message.withStyle(ChatFormatting.WHITE));
        sendMessageReloadAssets(source);
    }

    private static void sendMessageReloadAssets(CommandSourceStack source) {
        MutableComponent message = Component.literal("You need to reload the game assets for the changes to take effect.");
        sendMessage(source, message.withStyle(ChatFormatting.WHITE));
        MutableComponent message2 = Component.literal("You can do this by pressing F3 + T or by restarting the game.");
        sendMessage(source, message2.withStyle(ChatFormatting.WHITE));
    }

    public static MutableComponent start() {
        return Component.literal("[" + FullGrownCropMarker.MOD_NAME + "] ").withStyle(ChatFormatting.GOLD);
    }

    public static void sendMessage(CommandSourceStack source, Component message) {
        MutableComponent start = start().append(message);
        source.sendSuccess(start, false);
    }

    public static MutableComponent clickableConfig() {
        String fileName = ConfigManager.FILE_NAME;
        String filePath = ConfigManager.getConfigPath();
        MutableComponent mutableComponent = Component.literal(fileName);
        mutableComponent.withStyle(ChatFormatting.GREEN, ChatFormatting.UNDERLINE);
        mutableComponent.withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, filePath)));
        return mutableComponent;
    }

    public static MutableComponent clickableLink(String url, String displayText) {
        MutableComponent mutableComponent = Component.literal(displayText);
        mutableComponent.withStyle(ChatFormatting.GREEN, ChatFormatting.UNDERLINE);
        mutableComponent.withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)));
        return mutableComponent;
    }

    public static MutableComponent clickableLink(String url) {
        return clickableLink(url, url);
    }

}