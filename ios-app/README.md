# Care Commerce iOS

Care Commerce 고객용 iOS 앱입니다.

`desktop-web`과 같은 `/client-api/v1` 백엔드 API를 사용하는 네이티브 SwiftUI 클라이언트로 구성했습니다. 현재 단계에서는 포트폴리오 데모를 위해 로컬 데모 데이터 기반 화면과 API 클라이언트 골격을 함께 제공합니다.

## 실행 단위

```txt
ios-app
└── CareCommerceiOS.xcodeproj
```

## 주요 화면

- 홈: 케어 프로필, 진행 주문, 상담 상태 요약
- 상품: 카테고리 필터, 상품 목록, 장바구니 담기
- 장바구니: 담은 상품, 합계, 주문 생성
- 프로필: 상태, 임신 주차, 출산 예정일
- 상담: 상담 요청 상태

## 실행 방법

Xcode 설치 후:

```txt
ios-app/CareCommerceiOS.xcodeproj
```

를 열고 `CareCommerceiOS` scheme을 선택해 iPhone Simulator에서 실행합니다.

현재 로컬 환경은 Xcode Command Line Tools만 활성화되어 있어 `xcodebuild` 검증은 하지 못했습니다. Xcode 앱 설치 후 `xcode-select -s /Applications/Xcode.app/Contents/Developer`를 설정하면 CLI 빌드도 가능합니다.
