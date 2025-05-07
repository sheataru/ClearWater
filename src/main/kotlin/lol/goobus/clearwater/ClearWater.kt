package lol.goobus.clearwater

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.GameOptions
import net.minecraft.client.option.KeyBinding
import org.lwjgl.glfw.GLFW

class ClearWater : ModInitializer {

    private var keyBinding: KeyBinding? = null
    private var gammaSet = false
    private var originalGamma: Double = 0.5
    private var lastKeyState = false

    override fun onInitialize() {
        keyBinding = KeyBinding(
            "Toggle",
            GLFW.GLFW_KEY_G,
            "ClearWater"
        )

        KeyBindingHelper.registerKeyBinding(keyBinding)

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            onTick(client)
        }
    }

    private fun onTick(client: MinecraftClient) {
        val options = client.options
        val isKeyPressed = keyBinding?.isPressed == true

        if (isKeyPressed && !lastKeyState) {
            toggleGamma(options)
        }

        lastKeyState = isKeyPressed
    }

    private fun toggleGamma(options: GameOptions) {
        if (gammaSet) {
            options.gamma.value = originalGamma
        } else {
            originalGamma = options.gamma.value
            options.gamma.value = 1.0
        }
        gammaSet = !gammaSet
    }
}
