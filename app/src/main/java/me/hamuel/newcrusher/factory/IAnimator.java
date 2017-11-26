package me.hamuel.newcrusher.factory;

import android.animation.AnimatorSet;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellPair;
import me.hamuel.newcrusher.model.Coordinate;

import java.util.List;

public interface IAnimator {
    AnimatorSet animateRectTranslation(List<CellPair> cellPairs);
    AnimatorSet animateRectScale(List<Coordinate> cells);
}
