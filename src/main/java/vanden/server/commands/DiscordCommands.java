package vanden.server.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;

public class DiscordCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e){
        if (e.getName().equals("news")) {
            TextInput name = TextInput.create("title", "Заголовок", TextInputStyle.SHORT)
                    .build();
            TextInput password = TextInput.create("text", "Текст", TextInputStyle.PARAGRAPH)
                    .build();
            TextInput age = TextInput.create("image", "Картинка", TextInputStyle.PARAGRAPH)
                    .build();

            e.replyModal(Modal.create("newswindow", "Публикация новостей").addActionRows(ActionRow.of(name), ActionRow.of(password), ActionRow.of(age)).build()).queue();
        }
        if (e.getName().equals("emb")) {
            EmbedBuilder emb = new EmbedBuilder();
            System.out.println((e.getOption("type")));
            System.out.println((e.getOption("type")).getAsString());
            if (e.getOption("type").getAsString().equals("info")) {
                emb.setAuthor("Привет!");
                emb.addField("Pixel - ванильный майнкрафт сервер", "Попав на сервер, вы найдёте **ламповою** атмосферу, **отзывчивую** администрацию и **дружелюбных** игроков. Наш сервер **не изменяет** геймплейную составляющую игры, а все плагины лишь упрощают или дополняют процесс взаимодействия игроков между собой, к тому же, мы **не используем** сторонние сервисы и все имеющиеся плагины **написаны лично нами**, что увеличивает производительность и позволяет более гибко конфигурировать внутриигровые процессы. Мы стараемся редко производить вайпы мира, в основном это происходит голосованием самих игроков или при выходе новой версии майнкрафта. Надеюсь, тебе понравиться у нас! Приятной игры!" +
                        "\n" +
                        "\n" +
                        "**QnA:**\n" +
                        "Можно ли попасть на сервер без лицензии?\n" +
                        "**Да, сервер не требует лицензии для входа**\n" +
                        "\n" +
                        "Нужно ли платить за проходку?\n" +
                        "**Нет, весь функционал сервера доступен бесплатно**\n" +
                        "\n", false);
                emb.setThumbnail("https://media.discordapp.net/attachments/1057673012943532084/1057673870276050994/asdfasdfasdfasdf.png");
                emb.setImage("https://i.pinimg.com/564x/c8/b5/22/c8b5220ac281e4016253564291a63450.jpg");
                emb.setColor(Color.ORANGE);
                e.getChannel().sendMessageEmbeds(emb.build()).queue();
            }
            if (e.getOption("type").getAsString().equals("ticket")) {
                emb.addField("Подача заявки на сервер", "Для того чтобы получить доступ к серверу, заполни небольшую анкету", false);
                emb.setImage("https://media.discordapp.net/attachments/1057673012943532084/1057750014572634132/2022-12-28_23.00.20_2.png?width=960&height=540");
                emb.setColor(Color.ORANGE);
                e.getChannel().sendMessageEmbeds(emb.build()).addActionRow(Button.success("ticket", "Заявка")).queue();

            }
            e.deferReply().queue();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent e) {
        if (e.getModalId().equals("newswindow")) {

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.addField(e.getValue("title").getAsString(), e.getValue("text").getAsString(), false);
            embedBuilder.setImage(e.getValue("image").getAsString());

            e.getGuild().getTextChannelById("1056202246523736124").sendMessageEmbeds(embedBuilder.build()).queue();
            e.deferReply().queue();
        }
    }
}
