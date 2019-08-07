# Ed Book 안드로이드 샘플앱
### (with Aandroid Architecture Components - Java Only)
***

안드로이드 아키텍처 컴포넌트의 다양한 라이브리와 실 서비스 기준의 REST API를 이용해 부드럽고 무중단의 페이징을 다루는 샘플 앱입니다. **자바 기준**으로만 작성 된 소스입니다.

## Ed Book의 기본 시나리오
* [카카오 개발자 사이트][1]에서 제공하는 검색관련 OPEN API 중 [책검색][2] Rest API를 사용해서 기본적으로 서비스 데이터를 리스팅하고 다루는 시나리오입니다.<br><br>
* 카카오의 책검색 Rest API 사용하기 위해 개발자 사이트에서 본인의 개발자 계정으로 테스트 애플리케이션만들고 발급받은 REST API키를 사용하세요. KakaoBookApiService.java 소스 파일의 APP_KEY에 해당 본인계정의 REST API 키값으로 업데이트 해야 서버와의 정상적인 서비스 통신이 가능합니다.<br><br>
* Ed Book앱을 구동하면 메인 액티비티에서는 Tabbed layout을 사용하여 두개의 fragment로 구성됩니다.<br><br>
* 장바구니 탭에서는 장바구니에 담긴 책들의 총 합계 금액과 건수를 확인할 수 있습니다.<br><br>
* 세부항목 탭에서는 장바구니에 담긴 책들의 세부 정보를 RecyclerView를 통해 확인할 수 있습니다.<br><br>
* 장바구니 탭에서 검색할 제목을 입력하고 검색 버튼을 누르면 검색 액티비티가 호출되며 카카오 책검색 Rest api를 이용해 검색 아이템들을 페이징 처리해 RecyclerView를 통해 결과를 제공합니다. 아이템을 선택하면 검색 액티비티를 종료하고 선택 책 정보를 장바구니에 담는 기본 시나리오를 가집니다.<br><br>
* 장바구니 처리에 대한 비즈니스 로직은 기본적인 형태만 유지하며 데이터 관리는 휘발성입니다.<br><br>
* 이 샘플의 핵심은 전반적인 AAC와 범용적인 라이브러리들을 이용하여 Rest api를 다루는 온라인 서비스에서의 데이터의 페이징 처리의 효율적인 구현의 한 방법론에 대한 샘플 소스 공유입니다.



[1]:https://developers.kakao.com/
[2]:https://developers.kakao.com/docs/restapi/search#%EC%B1%85-%EA%B2%80%EC%83%89

<br><br>
## 주요 사용 라이브러리 및 설계 지향점

* **Java :** 안드로이드 공식 언어로 추가된 코틀린을 이용한 개발 예제들이 많이 늘어나고 있으며 실제 필드에서도 개발 전환이 많이 이루어 지고 있습니다. 하지만 기존 Java기준의 개발 서비스의 구조를 유지하면서 리팩토링을 통한 유지보수나 추가 개발 또한 상황에 따라서는 효율적이고 조직의 지향점이 될 수도 있습니다. 자바 기준으로 최신 아키텍쳐의 개념과 소스로의 적용도 코틀린 못지않게 학습과 활용 노하우를 가질 필요가 있습니다. 다양한 AAC라이브러리의 활용으로 Java 코드의 구조적인 간결을 지향합니다.<br><br>

* **REST Api :** 앱개발 기준으로 볼 때 안정적인 REST Api의 활용은 실제 개발이나 학습에서 매우 중요합니다. 잘 알려진 다양한 OPEN Api를 활용한 개념 확인과 개발 테스트도 중요합니다. 카카오 개발자 사이트의 오픈 Api 이용은 실제 서비스 개발이나 테스트에 많은 도움이 됩니다. Ed Book 샘플앱에서는 간단하게 활용할 수 있는 카카오의 책 검색 서비스를 이용한 앱개발 확인을 합니다.
카카오 책 검색 Api는 페이지 번호(키)를 기준으로 페이지당 로딩 아이템 수를 지정해 json 구조로 응답받는 기본 REST Api입니다.<br><br>

* **MVVM :** 스파케티 소스로 부터 MVC, MVP, MVVM까지 개발 소스의 구조적인 패턴변화는 계속 이루어지고 있습니다. 대세로 자리잡아가는 MVVM 구조 설계와 구글의 최신 라이브러리의 활용을 지향합니다. <br><br>

* **AndroidX :** 하위 호혼성을 위해 파편화된 다양한 Support Library의 활용 대신 AndroidX로의 전환을 지향합니다. <br><br>

* **Data Binding :** 데이터 변화에 대한 감시 루틴이나 뷰로의 반영을 위한 복잡한 코드를 줄이고 구조적인 최소화와 자동화 적용을 위한 Data binding의 사용을 지향해 코드의 구조적인 간결성과 효율성을 지향합니다.<br><br>

* **ViewModel :** 액티비티와 프래그먼트의 수명주기와 화면 로테이트 등 다양한 LifeCycle에 대한 데이터의 유지를 위한 복잡한 작업을 AAC의 ViewModel을 이용해 구조적인 효율과 안정성을 만들 수 있습니다. 액티비티와 프래그먼트에서 데이터를 바라보는 단일 창구역할을 하도록 ViewModel의 설계와 프래그먼트간의 데이터 공유에도 효율성을 가질 수 있습니다. View영역과 Model영역의 중간자 역할로 로직의 분리 설계의 용이성을 찾을 수 있는 ViewModel의 활용을 적극 지향합니다.<br><br>

* **LiveData :** 모델 영역의 데이터 변경을 ViewModel을 통해 뷰영역으로의 전달을 간편하고 쉽게 하기 위해 AAC의 LiveData 사용을 적극 지향합니다. 구조적인 간결성을 만들고 다양한 라이브사이클 변화의 리스크를 최소화하며 데이터 변화의 감지와 반영을 효율적으로 구현할 수 있습니다. <br><br>

* **Paging :** RecyclerView와 연동해서 페이징 처리를 쉽고 효율적으로 구현할 수 있는 AAC의 Paging 라이브러리를 활용 합니다. PagingList, PagedListAdapter를 통해 DataSource Factory와 DataSource를 로딩하고 리사이클러 뷰어와 연계해 효율적이고 빠른 서비스 데이터의 페이징 처리를 구현합니다. 카카오 책 검색 Api의 형태가 페이지번호(키)와 아이템 개수를 할당하는 구조이므로 PagedKeyedDataSource를 활용한 PagingList를 구현합니다.<br><br>

* **DataSource and DataSource.Factory:** 서비스 모델의 데이터 구조 정의와 데이터 로딩처리를 위해 DataSource와 DataSource Factory 를 이용합니다. Http 통신을 통한 데이터 호출 처리는 Retrofit2를 사용하고 DataSource와 연계해서 호출하도록 합니다. Retrofit에서 json 응답을 gson 으로 컨버팅 지정하여 바로 모델의 데이터 클래스 객체로 할당 하도록 해서 효율을 높이도록 합니다. <br><br>

* **RecyclerView :** 대용량의 리스팅 처리 및 가변적인 아이템 처리등에 일반적인 RecyclerView의 사용을 위해 PagedListAdapter, 일반적인 RecyclerViewAdapter등의 사용처리를 이용합니다. <br><br>

* **Retrofit2 :** 웹서버와의 통신을 위해 이제는 Retrofit2를 사용하지 않으면 이상할 정도로 일반화 되었습니다. Retrofit을 이용해 간편하게 정의하고 DataSource를 구성하도록 합니다.<br><br>

* **Picasso :** 원격지 image url 데이터를 비동기로 간편하게 로딩할 수 있는 서드파티 이미지 라이브러리 들도 다양합니다. 그 중 스퀘어사의 Picasso도 많이 사용하는 라이브러리 중의 하나 입니다. 리스트 아이템의 뷰잉 처리를 위해 Picasso, Retrofit, RecyclerView 등의 조합을 이용합니다.

<br><br>
구글에서 제공하는 최신 라이브러리의 이용과 범용적으로 많이 사용하는 3rd party 라이브러리의 활용으로 좀 더 간결한 코드설계와 빠르고 효율적인 구조를 만들어 갈 수 있습니다. 하나의 기본적인 구조 설계의 방법론으로 자리잡고 보편화 되고 있습니다. 개발자는 이러한 트랜드 라이브러리 활용법을 익히고 이용하면서 더 중요한 비즈니스 로직의 구현과 독창적인 알고리즘 구현 및 사용성 개선에 집중할 수 있습니다. 물론 시간이 지나고 다른 라이브러리 인터페이스와 새로운 개념이 나오면 다시 옛 방식으로 전락할 수 있겠지만 개발자에게 가장 중요한 것은 변화하는 트렌드나 개발 방법론을 끊임없이 학습하고 개선을 위해 능동적으로 대응하는 것이 자신의 경쟁력을 높이고 성과를 만들 수 있는 길이라고 생각합니다.


<br><br>
# 데모

<img src="./EdBook_demo.gif" width="320">

# 안드로이드 페이징 라이브러리 학습의 주요 정보

* [Paging library overview Android by Google][3]
* [Paging Library CodeLab][4]
* [PagingWithNetworkSample by Yigit Boyar][5]
* [Android Jetpack: manage infinite lists with RecyclerView and Paging (Google I/O '18)][6]
* [Android Jetpack: what's new in Architecture Components (Google I/O '18)][7]

 [3]: https://developer.android.com/topic/libraries/architecture/paging/
[4]: https://codelabs.developers.google.com/codelabs/android-paging/index.html?index=..%2F..%2Findex#0
[5]: https://github.com/googlesamples/android-architecture-components/tree/master/PagingWithNetworkSample
[6]: https://www.youtube.com/watch?v=BE5bsyGGLf4
[7]: https://www.youtube.com/watch?v=pErTyQpA390&t=862s


배포
--------------------------
자유롭게 참조 및 활용하세요. 앱개발에 도움이 되면 좋겠습니다.
다른 의견이나 개선 사항 있으면 메일로 의견 남겨 주세요~


개발자
------------
<img src="./edcho.png" width="80"><br>
* Ed Cho (Senior SW Developer)  - <edcho12@gmail.com>

라이선스
-------

    Copyright 2019 Ed Cho

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
