package dev.slne.augmented.common.base.bukkit.extensions

import dev.slne.augmented.common.base.bukkit.plugin.plugin
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.chat.ChatType
import net.kyori.adventure.chat.SignedMessage
import net.kyori.adventure.inventory.Book
import net.kyori.adventure.resource.ResourcePackInfoLike
import net.kyori.adventure.resource.ResourcePackRequest
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.SoundStop
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.TitlePart
import org.bukkit.entity.Player
import java.util.*

fun Player.clearResourcePacks() = plugin.audiences.player(this).clearResourcePacks()
fun Player.removeResourcePacks(request: ResourcePackRequest) =
    plugin.audiences.player(this).removeResourcePacks(request)

fun Player.removeResourcePacks(id: UUID, vararg others: UUID) =
    plugin.audiences.player(this).removeResourcePacks(id, *others)

fun Player.removeResourcePacks(ids: Iterable<UUID>) =
    plugin.audiences.player(this).removeResourcePacks(ids)

fun Player.sendResourcePacks(request: ResourcePackRequest) =
    plugin.audiences.player(this).sendResourcePacks(request)

fun Player.sendResourcePacks(first: ResourcePackInfoLike, vararg others: ResourcePackInfoLike) =
    plugin.audiences.player(this).sendResourcePacks(first, *others)

fun Player.sendMessage(message: Component) = plugin.audiences.player(this).sendMessage(message)
fun Player.sendMessage(message: Component, boundChatType: ChatType.Bound) =
    plugin.audiences.player(this).sendMessage(message, boundChatType)

fun Player.sendMessage(signedMessage: SignedMessage, boundChatType: ChatType.Bound) =
    plugin.audiences.player(this).sendMessage(signedMessage, boundChatType)

fun Player.deleteMessage(signedMessage: SignedMessage) =
    plugin.audiences.player(this).deleteMessage(signedMessage)

fun Player.deleteMessage(signature: SignedMessage.Signature) =
    plugin.audiences.player(this).deleteMessage(signature)

fun Player.sendActionBar(message: Component) = plugin.audiences.player(this).sendActionBar(message)
fun Player.sendPlayerListHeader(header: Component) =
    plugin.audiences.player(this).sendPlayerListHeader(header)

fun Player.sendPlayerListFooter(footer: Component) =
    plugin.audiences.player(this).sendPlayerListFooter(footer)

fun Player.sendPlayerListHeaderAndFooter(header: Component, footer: Component) =
    plugin.audiences.player(this).sendPlayerListHeaderAndFooter(header, footer)

fun Player.showTitle(title: Title) = plugin.audiences.player(this).showTitle(title)
fun <T : Any> Player.sendTitlePart(part: TitlePart<T>, value: T) =
    plugin.audiences.player(this).sendTitlePart(part, value)

fun Player.clearTitle() = plugin.audiences.player(this).clearTitle()
fun Player.resetTitle() = plugin.audiences.player(this).resetTitle()
fun Player.showBossBar(bar: BossBar) = plugin.audiences.player(this).showBossBar(bar)
fun Player.hideBossBar(bar: BossBar) = plugin.audiences.player(this).hideBossBar(bar)
fun Player.playSound(sound: Sound) = plugin.audiences.player(this).playSound(sound)
fun Player.playSound(sound: Sound, x: Double, y: Double, z: Double) =
    plugin.audiences.player(this).playSound(sound, x, y, z)

fun Player.playSound(sound: Sound, emitter: Sound.Emitter) =
    plugin.audiences.player(this).playSound(sound, emitter)

fun Player.stopSound(sound: Sound) = plugin.audiences.player(this).stopSound(sound)
fun Player.stopSound(stop: SoundStop) = plugin.audiences.player(this).stopSound(stop)
fun Player.openBook(book: Book) = plugin.audiences.player(this).openBook(book)