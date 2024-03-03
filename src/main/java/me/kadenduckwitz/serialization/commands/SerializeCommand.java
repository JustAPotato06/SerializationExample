package me.kadenduckwitz.serialization.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class SerializeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p) {
            ItemStack item = p.getInventory().getItemInMainHand();
            String encodedObject;

            if (!item.getType().equals(Material.AIR)) {
                try {
                    // Serialization
                    ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
                    BukkitObjectOutputStream bukkitOS = new BukkitObjectOutputStream(byteOS);
                    bukkitOS.writeObject(item);
                    bukkitOS.flush();
                    byte[] serializedObject = byteOS.toByteArray();

                    // Base64 Encoding
                    encodedObject = Base64.getEncoder().encodeToString(serializedObject);
                    p.sendMessage(encodedObject);
                    p.getInventory().removeItem(item);

                    // Base64 Decoding
                    serializedObject = Base64.getDecoder().decode(encodedObject);

                    // Deserialization
                    ByteArrayInputStream byteIS = new ByteArrayInputStream(serializedObject);
                    BukkitObjectInputStream bukkitIS = new BukkitObjectInputStream(byteIS);
                    ItemStack deserializedItem = (ItemStack) bukkitIS.readObject();
                    p.getInventory().addItem(deserializedItem);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return true;
    }
}