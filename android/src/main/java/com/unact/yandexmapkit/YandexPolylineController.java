package com.unact.yandexmapkit;

import androidx.annotation.NonNull;

import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PolylineMapObject;

import java.lang.ref.WeakReference;
import java.util.Map;

public class YandexPolylineController extends YandexMapObjectController implements MapObjectTapListener {
  public final PolylineMapObject polyline;
  private boolean consumeTapEvents = false;
  @SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
  private final WeakReference<YandexMapController> controller;
  public final String id;

  public YandexPolylineController(
    MapObjectCollection parent,
    Map<String, Object> params,
    WeakReference<YandexMapController> controller
  ) {
    PolylineMapObject polyline = parent.addPolyline(Utils.polylineFromJson(params));

    this.polyline = polyline;
    this.id = (String) params.get("id");
    this.controller = controller;

    polyline.setUserData(this.id);
    polyline.addTapListener(this);
    update(params);
  }

  @SuppressWarnings({"unchecked", "ConstantConditions"})
  public void update(Map<String, Object> params) {
    polyline.setGeometry(Utils.polylineFromJson(params));
    polyline.setGeodesic((Boolean) params.get("isGeodesic"));
    polyline.setZIndex(((Double) params.get("zIndex")).floatValue());
    polyline.setVisible((Boolean) params.get("isVisible"));
    polyline.setOutlineColor(((Number) params.get("outlineColor")).intValue());
    polyline.setOutlineWidth(((Double) params.get("outlineWidth")).floatValue());
    polyline.setStrokeColor(((Number) params.get("strokeColor")).intValue());
    polyline.setStrokeWidth(((Double) params.get("strokeWidth")).floatValue());
    polyline.setDashLength(((Double) params.get("dashLength")).floatValue());
    polyline.setDashOffset(((Double) params.get("dashOffset")).floatValue());
    polyline.setGapLength(((Double) params.get("gapLength")).floatValue());

    consumeTapEvents = (Boolean) params.get("consumeTapEvents");
  }

  public void remove() {
    polyline.getParent().remove(polyline);
  }

  @Override
  public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
    controller.get().mapObjectTap(id, point);

    return consumeTapEvents;
  }
}
