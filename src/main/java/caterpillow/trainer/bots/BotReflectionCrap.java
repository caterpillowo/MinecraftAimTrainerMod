package caterpillow.trainer.bots;

import caterpillow.trainer.scenarios.ScenarioPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class BotReflectionCrap {


    private Object obj;
    public Field botNumber;
    public Field spawnpoints;
    public Field position;
    public Method start;
    public Method onHit;
    public Method onTick;
    public Method onWorldRender;
//    public Method setReflectionObject;

    public BotReflectionCrap() {

    }

    public void setObj(Object obj) {
        this.obj = obj;

        try {
            botNumber = obj.getClass().getDeclaredField("botNumber");
            spawnpoints = obj.getClass().getDeclaredField("spawnpoints");
            position = obj.getClass().getDeclaredField("position");
            start = obj.getClass().getDeclaredMethod("start");
            onHit = obj.getClass().getDeclaredMethod("onHit", Vec3.class);
            onTick = obj.getClass().getDeclaredMethod("onTick");
            onWorldRender = obj.getClass().getDeclaredMethod("onWorldRender", float.class);
//            setReflectionObject = obj.getClass().getDeclaredMethod("setReflectionObject", BotReflectionCrap.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getBotNumber() {
        try {
            return botNumber.getInt(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<BlockPos> getSpawnpoints() {
        try {
            return (ArrayList<BlockPos>) spawnpoints.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setSpawnpoints(ArrayList<BlockPos> passedSpawnpoints) {
        try {
            spawnpoints.set(obj, passedSpawnpoints);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Vec3 getPosition() {
        try {
            return (Vec3) position.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void start() {
        try {
            start.invoke(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.getCause().printStackTrace();
        }
    }

    public void onHit(Vec3 hitVec) {
        try {
            onHit.invoke(obj, hitVec);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.getCause().printStackTrace();
        }
    }

    public void onTick() {
        try {
            onTick.invoke(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void onWorldRender(float partialTicks) {
        try {
            onWorldRender.invoke(obj, partialTicks);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

//    public void setReflectionObject() {
//        try {
//            setReflectionObject.invoke(obj, this);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }

}
