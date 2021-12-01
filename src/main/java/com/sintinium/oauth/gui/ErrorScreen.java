package com.sintinium.oauth.gui;

import com.google.common.base.Splitter;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class ErrorScreen extends OAuthScreen {

    private String message = null;
    private Throwable e = null;
    private boolean isInfo = false;

    public ErrorScreen(boolean isMs, String message) {
        super(new TextComponent("Error logging into " + (isMs ? "Microsoft." : "Mojang.")));
        this.message = message;
        System.err.println(message);
    }

    public ErrorScreen(boolean isMs, Throwable e) {
        super(new TextComponent("Error logging into " + (isMs ? "Microsoft." : "Mojang.")));
        this.e = e;
        e.printStackTrace();
    }

    public void setInfo() {
        this.isInfo = true;
    }

    @Override
    protected void init() {
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 2 + 60, 200, 20, CommonComponents.GUI_CANCEL, p_onPress_1_ -> {
            setScreen(new JoinMultiplayerScreen(new TitleScreen()));
        }));
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(PoseStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        Font font = Minecraft.getInstance().font;
        this.renderBackground(p_230430_1_);
        if (isInfo) {
            drawCenteredString(p_230430_1_, Minecraft.getInstance().font, this.title, this.width / 2, this.height / 2 - 40, 0xFFFFFF);
            drawCenteredString(p_230430_1_, Minecraft.getInstance().font, getMessage(), this.width / 2, this.height / 2 - 24, 0xFFFFFF);
        } else if (getMessage().toLowerCase().contains("no such host is known") || getMessage().toLowerCase().contains("connection reset")) {
            drawCenteredString(p_230430_1_, Minecraft.getInstance().font, this.title, this.width / 2, this.height / 2 - 40, 0xFFFFFF);
            drawCenteredString(p_230430_1_, Minecraft.getInstance().font, "The servers could be down or it could be an internet problem.", this.width / 2, this.height / 2 - 28, 0xFFFFFF);
            drawCenteredString(p_230430_1_, Minecraft.getInstance().font, "If you believe this is a bug please create an issue at", this.width / 2, this.height / 2 - 12, 0xFFFFFF);
            drawCenteredString(p_230430_1_, Minecraft.getInstance().font, "https://github.com/Sintinium/oauth with your latest log file.", this.width / 2, this.height / 2, 0xFFFFFF);
        } else {
            Component github = new TextComponent("Please create an issue at https://github.com/Sintinium/oauth with your log file.")
                    .setStyle(Style.EMPTY.setUnderlined(true));
            drawCenteredString(p_230430_1_, Minecraft.getInstance().font, "An error occurred. This could be a bug.", this.width / 2, this.height / 2 - 40, 0xFFFFFF);
            drawCenteredString(p_230430_1_, Minecraft.getInstance().font, github, this.width / 2, this.height / 2 - 28, 0xFFFFFF);
            float scale = .5f;
            p_230430_1_.scale(scale, scale, scale);
            String msg = getMessage();
            Iterable<String> messages = Splitter.fixedLength(Math.round(80 * (1f / scale))).limit(12).split(msg);
            int index = 0;
            for (String m : messages) {
                font.drawShadow(p_230430_1_, m, this.width / 2f - font.width(m) / 2f * scale, (this.height / 2f - 16f) * (1f / scale) + (index * 12f), 0xFF4444);
                index++;
            }
            p_230430_1_.scale(1f / scale, 1f / scale, 1f / scale);
        }

        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
    }

    private String getMessage() {
        String result = "";
        if (message != null) {
            result = message;
        } else if (e != null) {
            result = ExceptionUtils.getStackTrace(e);
        } else {
            return "Error getting error message.";
        }
        return result;
    }
}
