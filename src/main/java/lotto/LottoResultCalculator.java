package lotto;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

public class LottoResultCalculator {
    private List<Integer> winningNums;
    private int bonusNum;

    private LottoResultCalculator() {
    }

    private static class LottoResultCalculatorHelper {
        private static final LottoResultCalculator INSTANCE = new LottoResultCalculator();
    }

    public static LottoResultCalculator getInstance() {
        return LottoResultCalculatorHelper.INSTANCE;
    }

    public void setWinningNums(List<Integer> winningNums) {
        this.winningNums = winningNums;
    }

    public void setBonusNum(int bonusNum) {
        this.bonusNum = bonusNum;
    }

    public double calRateOfReturn(long winningAmount, int money) {
        return (double) winningAmount / money;
    }

    public long calWinningAmount(EnumMap<Rank, Integer> ranks) {
        return ranks.entrySet()
                .stream()
                .mapToLong(rankIntegerEntry -> {
                    Rank rank = rankIntegerEntry.getKey();
                    int cnt = rankIntegerEntry.getValue();
                    return rank.getAmount() * cnt;
                })
                .sum();
    }

    public EnumMap<Rank, Integer> calRankAll(List<Lotto> lottos) {
        EnumMap<Rank, Integer> ranks = new EnumMap<Rank, Integer>(Rank.class);
        Arrays.stream(Rank.values())
                .forEach(rank -> ranks.put(rank, 0));

        lottos.forEach(lotto -> {
            Rank rank = calRank(lotto);
            ranks.put(rank, ranks.get(rank) + 1);
        });

        return ranks;
    }

    private Rank calRank(Lotto lotto) {
        int matchedWinningNumCnt = compareWithWinningNums(lotto);
        int matchedBonusNumCnt = compareWithBonusNum(lotto);
        int rankOffset = 6;
        if (matchedBonusNumCnt == 1 && matchedWinningNumCnt == 5) {
            return Rank.SECOND_PRIZE;
        }

        if (matchedWinningNumCnt < 3) {
            return Rank.NOTHING;
        }

        return Rank.values()[rankOffset - matchedWinningNumCnt];
    }


    private int compareWithWinningNums(Lotto lotto) {
        int matchedNumCnt = (int) lotto.getNumbers()
                .stream()
                .filter(integer -> winningNums.contains(integer))
                .count();

        return matchedNumCnt;
    }

    private int compareWithBonusNum(Lotto lotto) {
        if (lotto.getNumbers()
                .contains(bonusNum)) {
            return 1;
        }

        return 0;
    }
}
