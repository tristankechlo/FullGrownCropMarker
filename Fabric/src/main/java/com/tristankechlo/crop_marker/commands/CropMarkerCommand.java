package com.tristankechlo.crop_marker.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.tristankechlo.crop_marker.FullGrownCropMarker;
import com.tristankechlo.crop_marker.config.ConfigManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public final class CropMarkerCommand {

    public static LiteralArgumentBuilder<FabricClientCommandSource> get() {
        return literal(FullGrownCropMarker.MOD_ID)
                .then(literal("config").requires((source) -> source.hasPermission(3))
                        .then(literal("reload").executes(CropMarkerCommand::configReload))
                        .then(literal("show").executes(CropMarkerCommand::configShow))
                        .then(literal("reset").executes(CropMarkerCommand::configReset)))
                .then(literal("github").executes(CropMarkerCommand::github))
                .then(literal("issue").executes(CropMarkerCommand::issue))
                .then(literal("wiki").executes(CropMarkerCommand::wiki))
                .then(literal("discord").executes(CropMarkerCommand::discord))
                .then(literal("curseforge").executes(CropMarkerCommand::curseforge))
                .then(literal("modrinth").executes(CropMarkerCommand::modrinth));
    }

    private static int configReload(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        ConfigManager.reloadConfig();
        ResponseHelper.sendMessageConfigReload(source);
        return 1;
    }

    private static int configShow(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        ResponseHelper.sendMessageConfigShow(source);
        return 1;
    }

    private static int configReset(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        ConfigManager.resetConfig();
        ResponseHelper.sendMessageConfigReset(source);
        return 1;
    }

    private static int github(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        Component link = ResponseHelper.clickableLink(FullGrownCropMarker.GITHUB_URL);
        Component message = new TextComponent("Check out the source code on GitHub: ").withStyle(ChatFormatting.WHITE).append(link);
        ResponseHelper.sendMessage(source, message);
        return 1;
    }

    private static int issue(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        Component link = ResponseHelper.clickableLink(FullGrownCropMarker.GITHUB_ISSUE_URL);
        Component message = new TextComponent("If you found an issue, submit it here: ").withStyle(ChatFormatting.WHITE).append(link);
        ResponseHelper.sendMessage(source, message);
        return 1;
    }

    private static int wiki(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        Component link = ResponseHelper.clickableLink(FullGrownCropMarker.GITHUB_WIKI_URL);
        Component message = new TextComponent("The wiki can be found here: ").withStyle(ChatFormatting.WHITE).append(link);
        ResponseHelper.sendMessage(source, message);
        return 1;
    }

    private static int discord(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        Component link = ResponseHelper.clickableLink(FullGrownCropMarker.DISCORD_URL);
        Component message = new TextComponent("Join the Discord here: ").withStyle(ChatFormatting.WHITE).append(link);
        ResponseHelper.sendMessage(source, message);
        return 1;
    }

    private static int curseforge(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        Component link = ResponseHelper.clickableLink(FullGrownCropMarker.CURSEFORGE_URL);
        Component message = new TextComponent("Check out the CurseForge page here: ").withStyle(ChatFormatting.WHITE).append(link);
        ResponseHelper.sendMessage(source, message);
        return 1;
    }

    private static int modrinth(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        Component link = ResponseHelper.clickableLink(FullGrownCropMarker.MODRINTH_URL);
        Component message = new TextComponent("Check out the Modrinth page here: ").withStyle(ChatFormatting.WHITE).append(link);
        ResponseHelper.sendMessage(source, message);
        return 1;
    }

}
