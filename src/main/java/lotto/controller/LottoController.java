package lotto.controller;

import static lotto.view.OutputView.printLottoList;
import static lotto.view.OutputView.printLottoQuantity;
import static lotto.view.OutputView.printRate;
import static lotto.view.OutputView.printResult;

import java.util.List;
import java.util.Map;
import lotto.domain.GenerateLotto;
import lotto.domain.Lotto;
import lotto.domain.LottoResult;
import lotto.domain.Lottos;
import lotto.domain.Money;
import lotto.domain.Rank;
import lotto.view.InputView;

public class LottoController {
    private final InputView inputView;
    private final LottoResult lottoResult;
    public LottoController(InputView inputView, LottoResult lottoResult) {
        this.inputView = inputView;
        this.lottoResult = lottoResult;
    }
    public void play() {
        // 구입 금액 입력
        boolean validInput = false;
        Money money = null;
        while (!validInput) {
            try {
                money = inputView.inputMoney();
                printLottoQuantity(money);
                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
        //로또 생성
        GenerateLotto generateLotto = new GenerateLotto();
        int count = money.getLottoQuantity();
        List<Lotto> lotto = generateLotto.getLottoNumbers(count);

        //발행 로또 출력
        Lottos lottos = new Lottos(lotto);
        printLottoList(lottos);

        //당첨 번호 입력
        Lotto winningLotto;
        List<Integer> winningNumbers;
        while (true) {
            try {
                winningNumbers = inputView.inputLottoWinningNumbers();
                winningLotto = new Lotto(winningNumbers);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        //보너스 번호 입력
        int bonusNumber;
        while (true) {
            try {
                bonusNumber = inputView.inputBonusNumber(winningNumbers);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        Map<Rank, Integer> result = lottoResult.compareLotto(lottos, winningLotto, bonusNumber);
        printResult(result);
        printRate(result, money);
    }


}
