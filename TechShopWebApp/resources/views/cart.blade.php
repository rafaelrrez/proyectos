@vite(['resources/js/app.js'])

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>{{ __('cart') }} | Tecno Market</title>
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

        <div class="container">
            <!-- HERO SECTION-->
            <section class="py-5 bg-light">
                <div class="container">
                    <div class="row px-4 px-lg-5 py-lg-4 align-items-center">
                        <div class="col-lg-6">
                            <h1 class="h2 text-uppercase mb-0">{{ __('cart') }}</h1>
                        </div>
                        <div class="col-lg-6 text-lg-end">
                            <nav aria-label="breadcrumb">
                                <ol class="breadcrumb justify-content-lg-end mb-0 px-0 bg-light">
                                    <li class="breadcrumb-item"><a class="text-dark" href="index.html">{{ __('home') }}</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">{{ __('cart') }}</li>
                                </ol>
                            </nav>
                        </div>
                    </div>
                </div>
            </section>
            <section class="py-5">
                <h2 class="h5 text-uppercase mb-4">{{ __('shopping_cart') }}</h2>
                <div class="row">
                    <div class="col-lg-8 mb-4 mb-lg-0">
                        <!-- CART TABLE-->
                        <div class="table-responsive mb-4">
                            <table class="table text-nowrap">
                                <thead class="bg-light">
                                    <tr>
                                        <th class="border-0 p-3" scope="col"> <strong
                                                class="text-sm text-uppercase">{{ __('detail') }}</strong></th>
                                        <th class="border-0 p-3" scope="col"> <strong
                                                class="text-sm text-uppercase">{{ __('price') }}</strong></th>
                                        <th class="border-0 p-3" scope="col"> <strong
                                                class="text-sm text-uppercase">{{ __('quantity_min') }}</strong></th>
                                        <th class="border-0 p-3" scope="col"> <strong
                                                class="text-sm text-uppercase">{{ __('total') }}</strong></th>
                                        <th class="border-0 p-3" scope="col"> <strong
                                                class="text-sm text-uppercase"></strong></th>
                                    </tr>
                                </thead>
                                <tbody class="border-0">
                                    @if(Session::has('success_message'))
                                    <div class="alert alert-success" role="alert">
                                        <strong>Success:</strong> {{ Session::get('success_message') }}
                                    </div>
                                    @endif
                                    @if(Session::has('error_message'))
                                    <div class="alert alert-danger" role="alert">
                                        <strong>Error:</strong> {{ Session::get('error_message') }}
                                    </div>
                                    @endif
                                    @if(Cart::count() > 0)
                                    @foreach(Cart::content() as $item)
                                    <tr>
                                        <th class="ps-0 py-3 border-light" scope="row">
                                            <div class="d-flex align-items-center"><a
                                                    class="reset-anchor d-block animsition-link" href="detail.html"><img
                                                        src="{!! asset('uploads/' . ($item->options->has('img') ? $item->options->get('img') : '')) !!}"
                                                        alt="..." width="70" />
                                                </a>
                                                <div class="ms-3"><strong class="h6"><a
                                                            class="reset-anchor animsition-link"
                                                            href="detail.html">{{$item->model->nombre}}</a></strong>
                                                </div>
                                            </div>
                                        </th>
                                        <td class="p-3 align-middle border-light">
                                            <p class="mb-0 small">{{$item->model->precio}}</p>
                                        </td>
                                        <td class="p-3 align-middle border-light">
                                            <p class="mb-0 small">{{$item->qty}}</p>
                                        </td>
                                        <td class="p-3 align-middle border-light">
                                            <p class="mb-0 small">{{$item->subtotal}}</p>
                                        </td>
                                        <td class="p-3 align-middle border-light">
                                            <form action="{{ route('cart.remove', $item->rowId) }}" method="POST">
                                                @csrf
                                                @method('POST')
                                                <button type="submit" class="reset-anchor btn btn-link"><i
                                                        class="fas fa-trash-alt small text-muted"></i></button>
                                            </form>
                                        </td>

                                    </tr>
                                    @endforeach
                                    @else
                                    <tr>
                                        <td class="p-3 align-middle border-light" colspan="5">
                                            <p class="mb-0 small">{{ __('no_item_in_cart') }}</p>
                                        </td>
                                    </tr>
                                    @endif
                                </tbody>
                            </table>
                        </div>
                        <!-- CART NAV-->
                        <div class="bg-light px-4 py-3">
                            <div class="row align-items-center text-center">
                                <div class="col-md-6 mb-3 mb-md-0 text-md-start"><a
                                        class="btn btn-link p-0 text-dark btn-sm" href="{{ route('shop') }}"><i
                                            class="fas fa-long-arrow-alt-left me-2"> </i>{{ __('continue_shopping') }}</a></div>
                                <div class="col-md-6 text-md-end">
                                    <form action="{{ route('checkout') }}" method="POST">
                                        @csrf
                                        <button type="submit" class="btn btn-primary">{{ __('go_to_checkout') }}</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- ORDER TOTAL-->
                    <div class="col-lg-4">
                        <div class="card border-0 rounded-0 p-lg-4 bg-light">
                            <div class="card-body">
                                <h5 class="text-uppercase mb-4">{{ __('cart') }} {{ __('total') }}</h5>
                                <ul class="list-unstyled mb-0">
                                    <li class="d-flex align-items-center justify-content-between"><strong
                                            class="text-uppercase small font-weight-bold">{{ __('subtotal') }}</strong><span
                                            class="text-muted small">${{Cart::subtotal()}}</span></li>
                                    <li class="border-bottom my-2"></li>
                                    <li class="d-flex align-items-center justify-content-between"><strong
                                            class="text-uppercase small font-weight-bold">{{ __('tax') }}</strong><span
                                            class="text-muted small">${{Cart::tax()}}</span></li>
                                    <li class="border-bottom my-2"></li>
                                    <li class="d-flex align-items-center justify-content-between mb-4"><strong
                                            class="text-uppercase small font-weight-bold">{{ __('total') }}</strong><span>${{Cart::total()}}</span>
                                    </li>
                                </ul>
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

        <!-- FontAwesome CSS - loading as last, so it doesn't block rendering-->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css"
            integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">

    </div>
</body>

</html>