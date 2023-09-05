package com.zhinan.zhouyi.fortune;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.energy.能量;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.luck.年运;
import com.zhinan.zhouyi.fate.luck.运势;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyFortune extends BaseFortune {
    @Getter
    @AllArgsConstructor
    public enum STUDY_DIRECTION {
        SOCIAL_SCIENCE("文科"), NATURAL_SCIENCE("理科"), ALL_GOOD("文理皆可");

        String name;

        public int getValue() {
            return ordinal();
        }
    }

    STUDY_DIRECTION direction;
    int midTermExamScore;
    int highTermExamScore;

    protected StudyFortune(八字 bazi) {
        super(bazi);
    }

    public static StudyFortune of(八字 bazi) {
        StudyFortune fortune = new StudyFortune(bazi);
        fortune.direction = StudyFortune.getStudyDirection(bazi);
        fortune.midTermExamScore  = fortune.getExamFortune(bazi.getBirthday().getYear() + 15);
        fortune.highTermExamScore = fortune.getExamFortune(bazi.getBirthday().getYear() + 18);
        return fortune;
    }

    public static STUDY_DIRECTION getStudyDirection(八字 bazi) {
        STUDY_DIRECTION direction = null;
        try {
            能量 energy = bazi.getEnergy();
            五行 ming   = energy.getMing();

            double socialScore  = energy.getValue(ming.get生()) + energy.getValue(五行.木) + energy.getValue(五行.火);
            double naturalScore = energy.getValue(ming.get泄()) + energy.getValue(五行.金) + energy.getValue(五行.水);

            if (Math.abs(socialScore - naturalScore) < 5) {
                direction = STUDY_DIRECTION.ALL_GOOD;
            } else {
                direction = socialScore > naturalScore ? STUDY_DIRECTION.SOCIAL_SCIENCE : STUDY_DIRECTION.NATURAL_SCIENCE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return direction;
    }

    public int getExamFortune(int year) {
        年运 luck = 年运.of(LocalDateTime.of(year - 2, 6, 6, 0, 0), bazi.getBirthday(), bazi.getSex().getValue());
        int firstYearScore  = ofLuck(luck).getScore();
        int secondYearScore = ofLuck(luck.getNext()).getScore();
        int finalYearScore  = ofLuck(luck.getNext().getNext()).getScore();
        return toInt(firstYearScore * 0.2 + secondYearScore * 0.3 + finalYearScore * 0.5);
    }

    public Fortune ofLuck(运势 luck) {
        能量 energy = bazi.getEnergy();
        double shiShangScore = getScore(energy.getMing().get泄(), luck, 0.6, 0.4);
        double yinShouScore  = getScore(energy.getMing().get生(), luck, 0.6, 0.4);
        double score = shiShangScore * 0.6 * (energy.isStrong() ? 0.8 : 0.3)
                     + yinShouScore  * 0.4 * (energy.isStrong() ? 0.3 : 0.8);

        if (luck.getParent() != null) {
            score = ofLuck(luck.getParent()).getScore() * 0.6 + score * 0.4;
        }
        return new Fortune(luck, toInt(score), false);
    }

    @Override
    public double getScore(八字 bazi) {
        能量 energy = bazi.getEnergy();
        五行 ming   = energy.getMing();

        double leakScore = energy.getValue(ming.get泄()) + energy.getValue(ming.get同()) * 0.5;
        double helpScore = energy.getValue(ming.get生()) + energy.getValue(ming.get克()) * 0.5;

        return leakScore * (energy.isStrong() ? 0.8 : 0.3) + helpScore * (energy.isStrong() ? 0.3 : 0.8);
    }

    @Override
    public GOOD_BAD judge() {
        return score > 610 * 0.4 * 0.8 ? GOOD_BAD.GOOD : GOOD_BAD.BAD;
    }
}
