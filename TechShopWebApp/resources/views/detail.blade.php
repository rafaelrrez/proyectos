@vite(['resources/js/app.js'])

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>{{ __('detail') }} | Tecno Market</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">
    <link rel="stylesheet" href="{{ asset('/css/shop/style.css') }}" id="theme-stylesheet">
    <!-- Favicon-->
    <link rel="icon" href="{{ asset('/img/favicon.png') }}" type="image/x-icon">
</head>

<body>
    <div class="page-holder bg-light">
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
                                <!-- Link--><a class="nav-link" href="{{ route('shop') }}">{{ __('shop') }}</a>
                            </li>
                            <li class="nav-item">
                                <!-- Link--><a class="nav-link active" href="detail.html">{{ __('detail') }}</a>
                            </li>
                        </ul>
                        <ul class="navbar-nav ms-auto">
                            <li class="nav-item">
                                <a href="{{ route('set_language', ['language' => 'en']) }}" class="nav-link">
                                    <img src="{{ asset('img/icon_uk.png') }}" alt="Bandera de UK" class="me-1 user-icon"
                                        style="max-width: 20px;">
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
                                            <i class="fas fa-sign-out-alt me-2"></i>Cerrar sesión
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
        <section class="py-5">
            <div class="container">
                <div class="row mb-5">
                    <div class="col-lg-6">
                        <!-- PRODUCT SLIDER-->
                        <div class="row m-sm-0">
                            <div class="col-sm-10 order-1 order-sm-2">
                                <!-- Remove Swiper related classes -->
                                <div class="product-slider">
                                    <div class="swiper-wrapper">
                                        <!-- Keep the image without the Swiper slide wrapper -->
                                        <img class="img-fluid" src="{!! asset("uploads/$producto->img") !!}"
                                        alt="...">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- PRODUCT DETAILS-->
                    <div class="col-lg-6">
                        <h1>{{$producto->nombre}}</h1><br>
                        <p class="text-muted lead">${{$producto->precio}}</p>
                        <div class="row align-items-stretch mb-4">
                            <div class="col-sm-5 pr-sm-0">
                                <div class="d-flex align-items-center justify-content-between">
                                    <span class="small text-uppercase text-gray mr-2 no-select">{{ __('quantity_min') }}:</span>
                                    <select class="form-select mb-0" id="selector_cantidad" name="selector_cantidad">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-sm-3 pl-sm-0 d-flex">
                                <form action="{{ route('cart_product') }}" method="post" class="w-100">
                                    @csrf
                                    <input type="hidden" name="producto_id" value="{{ $producto->id }}">
                                    <input type="hidden" name="producto_nombre" value="{{ $producto->nombre }}">
                                    <input type="hidden" name="producto_precio" value="{{ $producto->precio }}">
                                    <input type="hidden" name="producto_img" value="{{ $producto->img }}">
                                    <input type="hidden" name="producto_cantidad" id="producto_cantidad" value="1">

                                    <button type="submit" class="btn btn-primary w-100">{{ __('add_to_cart') }}</button>
                                </form>
                            </div>
                        </div>
                        <ul class="list-unstyled small d-inline-block">
                            <li class="px-3 py-2 mb-1 bg-white text-muted"><strong
                                    class="text-uppercase text-dark">{{ __('category') }}:</strong><a class="reset-anchor ms-2"
                                    href="#!">{{$producto->categoria}}</a></li>
                            <li class="px-3 py-2 mb-1 bg-white text-muted"><strong
                                    class="text-uppercase text-dark">{{ __('branch') }}:</strong><a class="reset-anchor ms-2"
                                    href="#!">{{$producto->marca}}</a></li>
                        </ul>
                    </div>
                </div>
                <!-- DETAILS TABS-->
                <ul class="nav nav-tabs border-0" id="myTab" role="tablist">
                    <li class="nav-item"><a class="nav-link text-uppercase active" id="description-tab"
                            data-bs-toggle="tab" href="#description" role="tab" aria-controls="description"
                            aria-selected="true">{{ __('description') }}</a></li>
                </ul>
                <div class="tab-content mb-5" id="myTabContent">
                    <div class="tab-pane fade show active" id="description" role="tabpanel"
                        aria-labelledby="description-tab">
                        <div class="p-4 p-lg-5 bg-white">
                            <h6 class="text-uppercase">{{ __('product_description') }}</h6>
                            <p class="text-muted text-sm mb-0">{{$producto->descripcion}}</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
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

        <script>
        // Añade un evento change al select para actualizar el campo oculto con la cantidad seleccionada
        document.getElementById('selector_cantidad').addEventListener('change', function() {
            document.getElementById('producto_cantidad').value = this.value;
        });
        </script>

        <!-- FontAwesome CSS - loading as last, so it doesn't block rendering-->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css"
            integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
    </div>

</body>

</html>