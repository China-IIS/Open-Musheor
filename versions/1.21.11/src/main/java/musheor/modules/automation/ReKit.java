package musheor.modules.automation;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.EnumSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.misc.Keybind;
import meteordevelopment.orbit.EventHandler;
import musheor.musheor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Identifier;
import obf.jU8xXhmkg;

public class ReKit extends Module {
   private final SettingGroup wSJeYsJcmjNZf = this.settings.getDefaultGroup();
   private final MinecraftClient i2Nu6XHKic = MinecraftClient.getInstance();
   public final Setting<ReKit.RekitMode> fStlZIvY9lS8 = this.wSJeYsJcmjNZf
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("mode")).description("Auto = triggers on container open. OnKey = triggers on keybind."))
               .defaultValue(ReKit.RekitMode.Auto))
            .build()
      );
   public final Setting<Keybind> QfZy2gWnKInxFPr = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.KeybindSetting.Builder)((meteordevelopment.meteorclient.settings.KeybindSetting.Builder)((meteordevelopment.meteorclient.settings.KeybindSetting.Builder)((meteordevelopment.meteorclient.settings.KeybindSetting.Builder)new meteordevelopment.meteorclient.settings.KeybindSetting.Builder()
                        .name("key"))
                     .description("Keybind to trigger rekit."))
                  .defaultValue(Keybind.none()))
               .visible(() -> this.fStlZIvY9lS8.get() == ReKit.RekitMode.OnKey))
            .build()
      );
   public final Setting<String> TJImwK2 = this.wSJeYsJcmjNZf
      .add(
         ((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)((meteordevelopment.meteorclient.settings.StringSetting.Builder)new meteordevelopment.meteorclient.settings.StringSetting.Builder()
                     .name("kit-name"))
                  .description("Name of the active kit. Leave blank to disable."))
               .defaultValue(""))
            .build()
      );
   private final HashMap<String, HashMap<Integer, Item>> vGAMA89H = new HashMap<>();
   private boolean qhulAUwd = false;
   private int BrmKXSYhWhqoB1 = -1;
   private ScreenHandler TXWyNb = null;

   public ReKit() {
      super(musheor.MAIN, "rekit", "Sorts inventory to match a saved kit loadout when opening a container or pressing a keybind.");
   }

   public WWidget getWidget(GuiTheme theme) {
      WVerticalList list = theme.verticalList();
      WButton saveBtn = (WButton)list.add(theme.button("Save current loadout")).widget();
      WButton clearBtn = (WButton)list.add(theme.button("Clear kit cache")).widget();
      saveBtn.action = () -> this.fStlZIvY9lS8((String)this.TJImwK2.get());
      clearBtn.action = this::fStlZIvY9lS8;
      return list;
   }

   public void onActivate() {
      String name = (String)this.TJImwK2.get();
      if (name.isEmpty()) {
         this.info("No kit selected.", new Object[0]);
      } else {
         HashMap<Integer, Item> kit = this.QfZy2gWnKInxFPr(name);
         if (kit == null) {
            this.info("Kit '§b%s§r' not found. Use §b.kit create§r to save one.", new Object[]{name});
         } else {
            this.vGAMA89H.put(name, kit);
            this.info("Loaded kit '§b%s§r' (§b%d§r slots).", new Object[]{name, kit.size()});
         }
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(OpenScreenEvent event) {
      if (this.fStlZIvY9lS8.get() == ReKit.RekitMode.Auto) {
         if (this.i2Nu6XHKic.player != null) {
            if (event.screen instanceof HandledScreen) {
               if (!(event.screen instanceof InventoryScreen)) {
                  ScreenHandler handler = this.i2Nu6XHKic.player.currentScreenHandler;
                  if (handler != null && handler != this.i2Nu6XHKic.player.playerScreenHandler) {
                     this.TXWyNb = handler;
                     this.BrmKXSYhWhqoB1 = 1;
                  }
               }
            }
         }
      }
   }

   @EventHandler
   private void fStlZIvY9lS8(Pre event) {
      if (this.i2Nu6XHKic.player != null) {
         if (this.BrmKXSYhWhqoB1 > 0 && --this.BrmKXSYhWhqoB1 == 0) {
            ScreenHandler current = this.i2Nu6XHKic.player.currentScreenHandler;
            if (current != null && current == this.TXWyNb) {
               this.fStlZIvY9lS8(this.TXWyNb);
            }

            this.TXWyNb = null;
         }

         if (this.fStlZIvY9lS8.get() == ReKit.RekitMode.OnKey) {
            boolean pressed = ((Keybind)this.QfZy2gWnKInxFPr.get()).isPressed();
            if (pressed && !this.qhulAUwd) {
               this.qhulAUwd = true;
               ScreenHandler handler = null;
               if (this.i2Nu6XHKic.currentScreen instanceof HandledScreen) {
                  ScreenHandler current = this.i2Nu6XHKic.player.currentScreenHandler;
                  if (current != null && current != this.i2Nu6XHKic.player.playerScreenHandler) {
                     handler = current;
                  }
               }

               this.fStlZIvY9lS8(handler);
            } else if (!pressed) {
               this.qhulAUwd = false;
            }
         }
      }
   }

   public void fStlZIvY9lS8(ScreenHandler containerHandler) {
      if (this.i2Nu6XHKic.player != null) {
         String name = (String)this.TJImwK2.get();
         if (!name.isEmpty()) {
            record MoveOp(int fStlZIvY9lS8, int QfZy2gWnKInxFPr, int TJImwK2) {


               @Override
               public final String toString() {
                  return "MoveOp[from=" + this.fStlZIvY9lS8 + ", to=" + this.QfZy2gWnKInxFPr + ", returnTo=" + this.TJImwK2 + "]";
               }
            }

            HashMap<Integer, Item> kit = this.vGAMA89H.computeIfAbsent(name, this::QfZy2gWnKInxFPr);
            if (kit == null) {
               this.info("Kit '§b%s§r' not found.", new Object[]{name});
            } else {
               Set<Integer> kitSlots = kit.keySet();
               int syncId = this.QfZy2gWnKInxFPr(containerHandler);
               int containerSize = containerHandler != null && containerHandler != this.i2Nu6XHKic.player.playerScreenHandler
                  ? containerHandler.slots.size() - 36
                  : 0;
               Item[] invItem = new Item[36];
               int[] invCount = new int[36];

               for (int i = 0; i < 36; i++) {
                  ItemStack s = this.i2Nu6XHKic.player.getInventory().getStack(i);
                  invItem[i] = s.getItem();
                  invCount[i] = s.getCount();
               }

               Item[] conItem = new Item[containerSize];
               int[] conCount = new int[containerSize];

               for (int i = 0; i < containerSize; i++) {
                  ItemStack s = containerHandler.getSlot(i).getStack();
                  conItem[i] = s.getItem();
                  conCount[i] = s.getCount();
               }

               List<MoveOp> plan = new ArrayList<>();
               Iterator var27 = kit.entrySet().iterator();

               while (true) {
                  int targetInv;
                  Item targetItem;
                  int maxCount;
                  int targetScreen;
                  int current;
                  while (true) {
                     if (!var27.hasNext()) {
                        for (MoveOp op : plan) {
                           if (!jU8xXhmkg.QfZy2gWnKInxFPr()) {
                              return;
                           }

                           if (op.QfZy2gWnKInxFPr() == -1) {
                              this.i2Nu6XHKic.interactionManager.clickSlot(syncId, op.fStlZIvY9lS8(), 0, SlotActionType.QUICK_MOVE, this.i2Nu6XHKic.player);
                           } else {
                              this.i2Nu6XHKic.interactionManager.clickSlot(syncId, op.fStlZIvY9lS8(), 0, SlotActionType.PICKUP, this.i2Nu6XHKic.player);
                              this.i2Nu6XHKic.interactionManager.clickSlot(syncId, op.QfZy2gWnKInxFPr(), 0, SlotActionType.PICKUP, this.i2Nu6XHKic.player);
                              if (op.TJImwK2() != -1) {
                                 this.i2Nu6XHKic.interactionManager.clickSlot(syncId, op.TJImwK2(), 0, SlotActionType.PICKUP, this.i2Nu6XHKic.player);
                              }
                           }
                        }

                        return;
                     }

                     Entry<Integer, Item> entry = (Entry<Integer, Item>)var27.next();
                     targetInv = entry.getKey();
                     targetItem = entry.getValue();
                     maxCount = targetItem.getMaxCount();
                     targetScreen = this.fStlZIvY9lS8(targetInv, containerHandler);
                     current = invItem[targetInv] == targetItem ? invCount[targetInv] : 0;
                     if (current < maxCount) {
                        if (invCount[targetInv] <= 0 || invItem[targetInv] == targetItem) {
                           break;
                        }

                        int freeSlot = this.fStlZIvY9lS8(invCount, kitSlots, targetInv);
                        if (freeSlot != -1) {
                           plan.add(new MoveOp(targetScreen, this.fStlZIvY9lS8(freeSlot, containerHandler), -1));
                           invItem[freeSlot] = invItem[targetInv];
                           invCount[freeSlot] = invCount[targetInv];
                        } else {
                           if (containerSize <= 0) {
                              continue;
                           }

                           plan.add(new MoveOp(targetScreen, -1, -1));
                        }

                        invItem[targetInv] = Items.AIR;
                        invCount[targetInv] = 0;
                        current = 0;
                        break;
                     }
                  }

                  int remaining = maxCount - current;

                  for (int i = 0; i < 36 && remaining > 0; i++) {
                     if (invItem[i] == targetItem && i != targetInv && invCount[i] != 0) {
                        Item kitAtI = kit.get(i);
                        if (kitAtI == null || kitAtI != targetItem) {
                           int fromScreen = this.fStlZIvY9lS8(i, containerHandler);
                           int moved = Math.min(invCount[i], remaining);
                           plan.add(new MoveOp(fromScreen, targetScreen, invCount[i] > remaining ? fromScreen : -1));
                           invCount[i] -= moved;
                           if (invCount[i] == 0) {
                              invItem[i] = Items.AIR;
                           }

                           invCount[targetInv] += moved;
                           invItem[targetInv] = targetItem;
                           remaining -= moved;
                        }
                     }
                  }

                  for (int i = 0; i < containerSize && remaining > 0; i++) {
                     if (conItem[i] == targetItem && conCount[i] != 0) {
                        int moved = Math.min(conCount[i], remaining);
                        plan.add(new MoveOp(i, targetScreen, conCount[i] > remaining ? i : -1));
                        conCount[i] -= moved;
                        if (conCount[i] == 0) {
                           conItem[i] = Items.AIR;
                        }

                        invCount[targetInv] += moved;
                        invItem[targetInv] = targetItem;
                        remaining -= moved;
                     }
                  }
               }
            }
         }
      }
   }

   private int fStlZIvY9lS8(int invSlot, ScreenHandler containerHandler) {
      if (containerHandler != null && containerHandler != this.i2Nu6XHKic.player.playerScreenHandler) {
         int containerSize = containerHandler.slots.size() - 36;
         return invSlot <= 8 ? containerSize + 27 + invSlot : containerSize + (invSlot - 9);
      } else {
         return invSlot <= 8 ? 36 + invSlot : invSlot;
      }
   }

   private int QfZy2gWnKInxFPr(ScreenHandler containerHandler) {
      return containerHandler != null && containerHandler != this.i2Nu6XHKic.player.playerScreenHandler
         ? containerHandler.syncId
         : this.i2Nu6XHKic.player.playerScreenHandler.syncId;
   }

   private int fStlZIvY9lS8(int[] invCount, Set<Integer> kitSlots, int exclude) {
      for (int i = 35; i >= 9; i--) {
         if (i != exclude && !kitSlots.contains(i) && invCount[i] == 0) {
            return i;
         }
      }

      for (int i = 8; i >= 0; i--) {
         if (i != exclude && !kitSlots.contains(i) && invCount[i] == 0) {
            return i;
         }
      }

      return -1;
   }

   public void fStlZIvY9lS8(String kitName) {
      if (kitName != null && !kitName.isEmpty()) {
         if (this.i2Nu6XHKic.player != null) {
            Map<String, String> kitData = new LinkedHashMap<>();

            for (int i = 0; i < 36; i++) {
               ItemStack stack = this.i2Nu6XHKic.player.getInventory().getStack(i);
               if (!stack.isEmpty()) {
                  kitData.put(String.valueOf(i), Registries.ITEM.getId(stack.getItem()).toString());
               }
            }

            File file = this.TJImwK2(kitName);
            if (file != null) {
               file.getParentFile().mkdirs();

               try (Writer writer = new FileWriter(file)) {
                  new Gson().toJson(kitData, writer);
               } catch (IOException e) {
                  this.error("Failed to save kit: %s", new Object[]{e.getMessage()});
                  return;
               }

               HashMap<Integer, Item> itemMap = this.fStlZIvY9lS8(kitData);
               this.vGAMA89H.put(kitName, itemMap);
               this.info("Saved kit '§b%s§r' (§b%d§r slots).", new Object[]{kitName, itemMap.size()});
            }
         }
      } else {
         this.info("Set a kit name first.", new Object[0]);
      }
   }

   public HashMap<Integer, Item> QfZy2gWnKInxFPr(String kitName) {
      File file = this.TJImwK2(kitName);
      if (file != null && file.exists()) {
         try (Reader reader = new FileReader(file)) {
            Type type = (new TypeToken<HashMap<String, String>>() {}).getType();
            HashMap<String, String> raw = (HashMap<String, String>)new Gson().fromJson(reader, type);
            return raw == null ? null : this.fStlZIvY9lS8(raw);
         } catch (Exception e) {
            this.error("Failed to load kit '§b%s§r': %s", new Object[]{kitName, e.getMessage()});
            return null;
         }
      } else {
         return null;
      }
   }

   private HashMap<Integer, Item> fStlZIvY9lS8(Map<String, String> raw) {
      HashMap<Integer, Item> map = new HashMap<>();

      for (Entry<String, String> e : raw.entrySet()) {
         try {
            int slot = Integer.parseInt(e.getKey());
            if (slot >= 0 && slot <= 35) {
               Item item = (Item)Registries.ITEM.get(Identifier.of(e.getValue()));
               if (item != Items.AIR) {
                  map.put(slot, item);
               }
            }
         } catch (NumberFormatException var7) {
         }
      }

      return map;
   }

   public File TJImwK2(String kitName) {
      if (kitName != null && !kitName.isEmpty()) {
         try {
            return new File(new File(new File(MeteorClient.FOLDER, "musheor"), "kits"), kitName + ".json");
         } catch (NullPointerException e) {
            return null;
         }
      } else {
         return null;
      }
   }

   public void fStlZIvY9lS8() {
      this.vGAMA89H.clear();
      this.info("Cleared kit cache.", new Object[0]);
   }

   public void wSJeYsJcmjNZf(String name) {
      this.vGAMA89H.remove(name);
      File file = this.TJImwK2(name);
      if (file != null && file.exists() && file.delete()) {
         this.info("Deleted kit '§b%s§r'.", new Object[]{name});
      }
   }

   public boolean i2Nu6XHKic(String name) {
      if (this.vGAMA89H.containsKey(name)) {
         return true;
      }

      File file = this.TJImwK2(name);
      return file != null && file.exists();
   }

   public List<String> QfZy2gWnKInxFPr() {
      File kitsDir = new File(new File(MeteorClient.FOLDER, "musheor"), "kits");
      if (!kitsDir.exists()) {
         return Collections.emptyList();
      }

      File[] files = kitsDir.listFiles((d, n) -> n.endsWith(".json"));
      if (files == null) {
         return Collections.emptyList();
      }

      List<String> names = new ArrayList<>();

      for (File f : files) {
         names.add(f.getName().replace(".json", ""));
      }

      return names;
   }

   public enum RekitMode {
      Auto,
      OnKey;

      RekitMode() {
      }
   }
}
