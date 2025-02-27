@vite(['resources/js/app.js'])

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>{{ __('home') }} | Tecno Market</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">
    <link rel="stylesheet" href="{{ asset('/css/shop/style.css') }}" id="theme-stylesheet">
    <!-- Favicon-->
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
                                <a class="nav-link active" href="{{ route('index') }}">{{ __('home') }}</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="{{ route('shop') }}">{{ __('shop') }}</a>
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
                            <li class="nav-item">
                                <a class="nav-link" href="{{ route('login-signup') }}">
                                    <i class="fas fa-user me-1 text-gray fw-normal"></i>{{ __('login') }}
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
        </header>
        <!-- HERO SECTION-->
        <div class="container">
            <section class="hero pb-3 bg-cover bg-center d-flex align-items-center"
                style="background: url(../public/img/welcome/hero-banner-alt.jpg)">
                <div class="container py-5">
                    <div class="row px-4 px-lg-5">
                        <div class="col-lg-6">
                            <p class="text-muted small text-uppercase mb-2">{{ __('welcome_message') }}</p>
                            <h1 class="h2 text-uppercase mb-3">{{ __('enjoy_discount') }}</h1><a class="btn btn-primary"
                                href="{{ route('shop') }}">{{ __('explore_collection') }}</a>
                        </div>
                    </div>
                </div>
            </section>
            <!-- CATEGORIES SECTION-->
            <section class="pt-5">
                <header class="text-center">
                    <p class="small text-muted small text-uppercase mb-1">{{ __('categories') }}</p>
                    <h2 class="h5 text-uppercase mb-4">{{ __('browse_categories') }}s</h2>
                </header>
                <div class="row">
                    <div class="col-md-4"><a class="category-item"
                            href="{{ route('shop-category', ['category' => 'Graphic cards']) }}"><img class="img-fluid"
                                src="../public/img/welcome/cat-img-1.jpg" alt="" /><strong
                                class="category-item-title">{{ __('graphic_cards') }}</strong></a>
                    </div>
                    <div class="col-md-4"><a class="category-item mb-4"
                            href="{{ route('shop-category', ['category' => 'Televisions']) }}"><img class="img-fluid"
                                src="../public/img/welcome/cat-img-2.jpg" alt="" /><strong
                                class="category-item-title">{{ __('televisions') }}</strong></a><a class="category-item"
                            href="{{ route('shop-category', ['category' => 'Laptops']) }}"><img class="img-fluid"
                                src="../public/img/welcome/cat-img-3.jpg" alt="" /><strong
                                class="category-item-title">{{ __('laptops') }}</strong></a>
                    </div>
                    <div class="col-md-4"><a class="category-item"
                            href="{{ route('shop-category', ['category' => 'Headphones']) }}"><img class="img-fluid"
                                src="../public/img/welcome/cat-img-4.jpg" alt="" /><strong
                                class="category-item-title">{{ __('headphones') }}</strong></a>
                    </div>
                </div>
            </section>
            <!-- TRENDING PRODUCTS-->
            <section class="py-5">
                <header>
                    <p class="small text-muted small text-uppercase mb-1">{{ __('trending_products') }}</p>
                    <h2 class="h5 text-uppercase mb-4">{{ __('made_hard_way') }}</h2>
                </header>
                <div class="row">
                    <!-- PRODUCT-->
                    @foreach($productos as $prod)
                    <div class="col-xl-3 col-lg-4 col-sm-6">
                        <div class="product text-center">
                            <div class="position-relative mb-3">
                                <div class="badge text-white bg-"></div><a class="d-block"
                                    href="{{ route('productos/detalles',$prod->id) }}"><img class="img-fluid w-100"
                                        src="{!! asset("uploads/$prod->img") !!}"
                                    alt="..."></a>
                                <div class="product-overlay">
                                    <form action="{{ route('cart_product') }}" method="post" class="w-100">
                                        @csrf
                                        <input type="hidden" name="producto_id" value="{{ $prod->id }}">
                                        <input type="hidden" name="producto_nombre" value="{{ $prod->nombre }}">
                                        <input type="hidden" name="producto_precio" value="{{ $prod->precio }}">
                                        <input type="hidden" name="producto_img" value="{{ $prod->img }}">
                                        <input type="hidden" name="producto_cantidad" id="producto_cantidad" value="1">

                                        <button type="submit" class="btn btn-primary">{{ __('add_to_cart') }}</button>
                                    </form>
                                </div>
                            </div>
                            <h6> <a class="reset-anchor" href="detail.html">{{$prod->nombre}}</a></h6>
                            <p class="small text-muted">${{$prod->precio}}</p>
                        </div>
                    </div>
                    @endforeach
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
    </div>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css"
        integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">

</body>

</html>