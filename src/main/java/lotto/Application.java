package lotto;


import java.util.EnumMap;
import java.util.List;

public class Application {
    public static int LOTTERY_PRICE = 1000;

    public static void main(String[] args) {
        InputOutput inputOutput = InputOutput.getInstance();
        LottoPublisher lottoPublisher = LottoPublisher.getInstance();

        int money = inputOutput.getMoney();
        List<Lotto> lottos = lottoPublisher.publish(money);

        inputOutput.printLottos(lottos);

        List<Integer> winningNums = inputOutput.getWinningNums();
        int bonusNum = inputOutput.getBonusNum();
        inputOutput.validateBonusNum(bonusNum, winningNums);

        LottoResultCalculator lottoResultCalculator = LottoResultCalculator.getInstance();
        lottoResultCalculator.setWinningNums(winningNums);
        lottoResultCalculator.setBonusNum(bonusNum);
        EnumMap<Rank, Integer> ranks = lottoResultCalculator.calRankAll(lottos);
    }
}
