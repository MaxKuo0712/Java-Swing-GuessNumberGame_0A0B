# Java-Swing-GuessNumberGame_0A0B
一個簡易小遊戲，猜數字，幾Ａ幾Ｂ的版本
<img width="752" alt="1-示意圖" src="https://user-images.githubusercontent.com/68499661/159153121-3d3dd52c-ac94-45d1-a61f-bb0b373bdd9e.png">

## 練習技術
1. Java語法
2. Java OOP
3. Java Swing

## 遊戲說明-遊戲流程說明
1. 輸入要玩幾位數(不能超過9位數，因遊戲答案是每個數字不重複)
<img width="640" alt="2-玩幾位數" src="https://user-images.githubusercontent.com/68499661/159153205-7b418ced-e287-432f-a87a-f1b215b9730a.png">

2. 開始遊戲，猜數字
位置及數字都正確：加1A
位置不正確，但數字有出現在答案中：加1B

![3-猜數字_AdobeCreativeCloudExpress](https://user-images.githubusercontent.com/68499661/159153298-a6402e5a-f5a8-44c4-b3e2-db647ebd6c2f.gif)

3. 成功猜對

![4-答對了_AdobeCreativeCloudExpress](https://user-images.githubusercontent.com/68499661/159153811-249ef884-79d2-4add-8515-9f91edbb2f01.gif)

4. 10回合內沒猜到就算輸(最後一回合會提醒只剩下最後一局)

![5-輸了_AdobeCreativeCloudExpress](https://user-images.githubusercontent.com/68499661/159153771-c68c5d78-d946-4d8f-9f2c-d4ea024ec479.gif)

5. 贏或是輸，都會問你要不要再玩一次，會重新回到第一步(輸入要玩幾位數)
<img width="640" alt="6-再玩一次" src="https://user-images.githubusercontent.com/68499661/159153860-fa313bfe-a154-400b-ae3b-448376854702.png">

## 遊戲說明-遊戲注意事項
1. 輸入必須為數字(用鍵盤事件限制)

![8-數字限制_AdobeCreativeCloudExpress](https://user-images.githubusercontent.com/68499661/159153962-f49aee35-e5dd-4664-b33e-6b5b239ce157.gif)

2. 輸入必須於遊戲限制的數字範圍內
<img width="640" alt="7-遊戲範圍" src="https://user-images.githubusercontent.com/68499661/159153900-67865174-6f34-49d7-8a70-75f987cfa36d.png">

