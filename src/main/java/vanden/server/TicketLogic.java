package vanden.server;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.bukkit.Bukkit;
import org.json.simple.JSONObject;

import java.awt.*;

public class TicketLogic extends ListenerAdapter {
    @Override
    public void onButtonInteraction(ButtonInteractionEvent e) {
        if (e.getButton().getId().equals("ticket")) {

            TextInput name = TextInput.create("name", "Никнейм", TextInputStyle.SHORT)
                    .setPlaceholder("Vasya_Pupkin")
                    .build();
            TextInput password = TextInput.create("password", "Пароль", TextInputStyle.SHORT)
                    .setPlaceholder("h9gf8ashdf897ashfd87")
                    .build();
            TextInput age = TextInput.create("age", "Возраст", TextInputStyle.SHORT)
                    .setPlaceholder("54")
                    .build();

            e.replyModal(Modal.create("ticketwindow", "Заявка на сервер").addActionRows(ActionRow.of(name), ActionRow.of(password), ActionRow.of(age)).build()).queue();
        }
        if (e.getButton().getId().equals("accept")) {
            JSONObject playerobject = new JSONObject();
            playerobject.put("password", e.getMessage().getEmbeds().get(0).getFields().get(1).getValue());
            playerobject.put("discord", e.getMessage().getEmbeds().get(0).getFields().get(2).getValue());
            Server.playerdata.put(e.getMessage().getEmbeds().get(0).getFields().get(0).getValue(), playerobject);
            Member fetchedMember = e.getGuild().retrieveMemberById(e.getMessage().getEmbeds().get(0).getFields().get(2).getValue()).complete();
            PrivateChannel privateChannel = fetchedMember.getUser().openPrivateChannel().complete();

            EmbedBuilder emb = new EmbedBuilder();
            emb.setAuthor(fetchedMember.getUser().getName(), fetchedMember.getUser().getAvatarUrl());
            emb.addField("Привет, твоя заявка одобрена!", "Айпи сервера - pixelmc.cf\nВерсия - 1.19.3\n\nНе забудь свой пароль! - ||" + e.getMessage().getEmbeds().get(0).getFields().get(1).getValue() + "||", false);
            emb.setThumbnail("https://static.wikia.nocookie.net/minecraft_gamepedia/images/f/f2/Paper_JE2_BE2.png/revision/latest?cb=20190606162534");
            emb.setColor(Color.GREEN);

            privateChannel.sendMessageEmbeds(emb.build()).queue();
            Bukkit.getOfflinePlayer(e.getMessage().getEmbeds().get(0).getFields().get(0).getValue()).setWhitelisted(true);
            e.reply("Добавлен браток \n" + Server.playerdata.toString()).queue();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent e) {
        if (e.getModalId().equals("ticketwindow")) {
            EmbedBuilder ticket = new EmbedBuilder();
            ticket.addField("Ник", e.getValue("name").getAsString(), false);
            ticket.addField("Пароль", e.getValue("password").getAsString(), false);
            ticket.addField("Айди дса", e.getMember().getUser().getId(), false);
            ticket.addField("Возраст", e.getValue("age").getAsString(), false);
            ticket.setTitle("Ноавая заявка!");
            ticket.setColor(Color.GREEN);
            e.getGuild().getTextChannelById("1055112762021388298").sendMessageEmbeds(ticket.build()).addActionRow(
                    Button.success("accept", "+"),
                    Button.danger("decline", "-")
            ).queue();
            e.reply("Заявка подана и будет рассмотрена в ближайшее время!").setEphemeral(true).queue();
        }
    }

}
