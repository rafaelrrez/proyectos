@vite(['resources/js/app.js'])

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>{{ __('shop') }} | Tecno Market</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">
    <link rel="stylesheet" href="{{ asset('/css/shop/style.css') }}" id="theme-stylesheet">
    <link rel="icon" href="{{ asset('/img/favicon.png') }}" type="image/x-icon">
</head>

<body>
    <div class="page-holder">
        <!-- navbar-->
        <header class="header bg-white">
            <div class="container px-lg-3">
                <nav class="navbar navbar-expand-lg navbar-light py-3 px-lg-0"><a class="navbar-brand"
                        href="{{ route('index') }}"><img src="{{ asset('/img/logo.png') }}" alt="Tecno Market Logo"
                            class="img-fluid" style="max-width: 100px;"></a>
                    <button class="navbar-toggler navbar-toggler-end" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation"><span
                            class="navbar-toggler-icon"></span></button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav me-auto">
                            <li class="nav-item">
                                <!-- Link--><a class="nav-link" href="{{ route('index') }}">{{ __('home') }}</a>
                            </li>
                            <li class="nav-item">
                                <!-- Link--><a class="nav-link active" href="{{ route('shop') }}">{{ __('shop') }}</a>
                            </li>
                        </ul>
                        <ul class="navbar-nav ms-auto">
                            <li class="nav-item">
                                <a href="{{ route('set_language', ['language' => 'en']) }}" class="nav-link">
                                    <img src="{{ asset('img/icon_uk.png') }}" alt="Bandera de España"
                                        class="me-1 user-icon" style="max-width: 20px;">
                                </a>
                            </li>
                            <li class="nav-item">
                                <a href="{{ route('set_language', ['language' => 'es']) }}" class="nav-link">
                                    <img src="{{ asset('img/icon_espana.png') }}" alt="Bandera de España"
                                        class="me-1 user-icon" style="max-width: 20px;">
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="{{ route('cart') }}">
                                    <i class="fas fa-dolly-flatbed me-1 text-gray"></i>{{ __('cart') }}
                                    <small class="text-gray fw-normal">({{ Cart::count() }})</small>
                                </a>
                            </li>
                            @if (Auth::check())
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                                    data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <i class="fas fa-user me-2 text-gray"></i>{{ Auth::user()->name }}
                                </a>
                                <div class="dropdown-menu" aria-labelledby="userDropdown">
                                    <h6 class="dropdown-header">{{ Auth::user()->name }}</h6>
                                    <div class="dropdown-divider"></div>
                                    <form method="POST" action="{{ route('logout') }}">
                                        @csrf
                                        <button type="submit" class="dropdown-item">
                                            <i class="fas fa-sign-out-alt me-2"></i>{{ __('logout') }}
                                        </button>
                                    </form>
                                </div>
                            </li>
                            @else
                            <li class="nav-item">
                                <a class="nav-link" href="{{ route('login-signup') }}">
                                    <i class="fas fa-user me-1 text-gray fw-normal"></i>{{ __('login') }}
                                </a>
                            </li>
                            @endif
                        </ul>
                    </div>
                </nav>
            </div>
        </header>

        <!-- HERO SECTION-->
        <section class="py-5 bg-light">
            <div class="container">
                <div class="row px-4 px-lg-5 py-lg-4 align-items-center">
                    <div class="col-lg-6">
                        <h1 class="h2 text-uppercase mb-0">Shop</h1>
                    </div>
                    <div class="col-lg-6 text-lg-end">
                        <nav aria-label="breadcrumb">
                            <ol class="breadcrumb justify-content-lg-end mb-0 px-0 bg-light">
                                <li class="breadcrumb-item"><a class="text-dark"
                                        href="{{ route('index') }}">{{ __('home') }}</a>
                                </li>
                                <li class="breadcrumb-item active" aria-current="page">{{ __('shop') }}</li>
                            </ol>
                        </nav>
                    </div>
                </div>
            </div>
        </section>
        <section class="py-5">
            <div class="container p-0">
                <div class="row">
                    <!-- SHOP SIDEBAR-->
                    <div class="col-lg-3 order-2 order-lg-1">
                        <h5 class="text-uppercase mb-4">{{ __('categories') }}</h5>
                        <div class="py-2 px-4 bg-dark text-white mb-3"><strong
                                class="small text-uppercase fw-bold">{{ __('electronics') }}</strong></div>
                        <ul class="list-unstyled small text-muted ps-lg-4 font-weight-normal mb-5">
                            <li class="mb-2"><a class="reset-anchor"
                                    href="{{ route('shop') }}">{{ __('all categories') }}</a>
                            </li>
                            <li class="mb-2"><a class="reset-anchor"
                                    href="{{ route('shop-category', ['category' => 'Graphic cards']) }}">{{ __('graphic_cards') }}</a>
                            </li>
                            <li class="mb-2"><a class="reset-anchor"
                                    href="{{ route('shop-category', ['category' => 'Televisions']) }}">{{ __('televisions') }}</a>
                            </li>
                            <li class="mb-2"><a class="reset-anchor"
                                    href="{{ route('shop-category', ['category' => 'Laptops']) }}">{{ __('laptops') }}</a>
                            </li>
                            <li class="mb-2"><a class="reset-anchor"
                                    href="{{ route('shop-category', ['category' => 'Headphones']) }}">{{ __('headphones') }}</a>
                            </li>
                        </ul>
                    </div>
                    <!-- SHOP LISTING-->
                    <div class="col-lg-9 order-1 order-lg-2 mb-5 mb-lg-0">
                        <div class="row mb-3 align-items-center">
                            <div class="col-lg-6 mb-2 mb-lg-0">
                                <p class="text-sm text-muted mb-0">
                                    {{ $productos->firstItem() }}–{{ $productos->lastItem() }} {{ __('of') }}
                                    {{ $productos->total() }} {{ __('results') }}
                                </p>
                            </div>
                        </div>
                        <div class="row">
                            <!-- PRODUCT-->
                            @foreach($productos as $prod)
                            <div class="col-lg-4 col-sm-6">
                                <div class="product text-center">
                                    <div class="mb-3 position-relative">
                                        <div class="badge text-white bg-"></div><a class="d-block"
                                            href="{{ route('productos/detalles',$prod->id) }}"><img
                                                class="img-fluid w-100" src="{!! asset("uploads/$prod->img") !!}"
                                            alt="..."></a>
                                        <div class="product-overlay">
                                            <ul class="mb-0 list-inline">
                                                <form action="{{ route('cart_product') }}" method="post" class="w-100">
                                                    @csrf
                                                    <input type="hidden" name="producto_id" value="{{ $prod->id }}">
                                                    <input type="hidden" name="producto_nombre"
                                                        value="{{ $prod->nombre }}">
                                                    <input type="hidden" name="producto_precio"
                                                        value="{{ $prod->precio }}">
                                                    <input type="hidden" name="producto_img" value="{{ $prod->img }}">
                                                    <input type="hidden" name="producto_cantidad" id="producto_cantidad"
                                                        value="1">

                                                    <button type="submit" class="btn btn-primary">{{ __('add_to_cart') }}</button>
                                                </form>
                                            </ul>
                                        </div>
                                    </div>
                                    <h6> <a class="reset-anchor" href="detail.html">{{$prod->nombre}}</a></h6>
                                    <p class="small text-muted">${{$prod->precio}}</p>
                                </div>
                            </div>
                            @endforeach
                        </div>
                        <!-- PAGINATION-->
                        <div class="pagination justify-content-center justify-content-lg-end">
                            {{ $productos->onEachSide(1)->links('pagination.custom') }}
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <footer class="bg-dark text-white">
        <div class="container py-4">
            <div class="row py-5">
                <div class="col-md-4 mb-3 mb-md-0">
                    <h6 class="text-uppercase mb-3">{{ __('customer_services') }}</h6>
                    <ul class="list-unstyled mb-0">
                        <li><a class="footer-link" href="#!">{{ __('help_contact') }}</a></li>
                        <li><a class="footer-link" href="#!">{{ __('terms_conditions') }}</a></li>
                    </ul>
                </div>
            </div>
            <div class="border-top pt-4" style="border-color: #1d1d1d !important">
                <div class="row">
                    <div class="col-md-6 text-center text-md-start">
                        <p class="small text-muted mb-0">{{ __('rights_reserved') }}</p>
                    </div>
                    <div class="col-md-6 text-center text-md-end">
                        <p class="small text-muted mb-0">{{ __('template_designed_by') }}</p>
                    </div>
                </div>
            </div>
        </div>
    </footer>

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css"
        integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
    </div>
</body>

</html>