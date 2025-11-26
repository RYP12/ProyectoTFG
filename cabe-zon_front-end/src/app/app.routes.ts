import { Routes } from '@angular/router';
import {Catalogo} from './BUNDLES/CatalogoBundle/pages/catalogo/catalogo';
import {Login} from './BUNDLES/LoginBundle/pages/login/login';
import {AboutUs} from './BUNDLES/SobreNosotrosBundle/pages/about-us/about-us';
import {Funko} from './BUNDLES/FunkoBundle/pages/funko/funko';
import {Confirmar} from './BUNDLES/ConfirmacionPedidoBundle/pages/confirmar/confirmar';
import {EdicionLimitada} from './BUNDLES/EdicionLimitadaBundle/pages/edicion-limitada/edicion-limitada';
import {OwnerControlPedidos} from './BUNDLES/OwnerBundle/pages/owner-control-pedidos/owner-control-pedidos';
import {OwnerControlProductos} from './BUNDLES/OwnerBundle/pages/owner-control-productos/owner-control-productos';
import {OwnerControlPedidoForm} from './BUNDLES/OwnerBundle/forms/owner-control-pedido-form/owner-control-pedido-form';
import {
  OwnerControlProductoForm
} from './BUNDLES/OwnerBundle/forms/owner-control-producto-form/owner-control-producto-form';
import {
  OwnerControlClienteForm
} from './BUNDLES/OwnerBundle/forms/owner-control-cliente-form/owner-control-cliente-form';
import {Error} from './SHARED/ErrorBundle/pages/error/error';
import {StarterPage} from './BUNDLES/StarterBundle/pages/starter-page/starter-page';
import {CustomerControl} from './BUNDLES/CustomerBundle/pages/customer-control/customer-control/customer-control';

export const routes: Routes = [
  {
    path: '',
    component: StarterPage,
  },
  {
    path: 'catalogo',
    component: Catalogo,
  },
  {
    path: 'login',
    component: Login,
  },
  {
    path: 'sobre-nosotros',
    component: AboutUs,
  },
  {
    path: 'funko/:id',
    component: Funko,
  },
  {
    path: 'confirmacion-de-pedido',
    component: Confirmar,
  },
  {
    path: 'edicion-limitada',
    component: EdicionLimitada,
  },
  {
    path: 'admin/pedidos',
    component: OwnerControlPedidos,
  },
  {
    path: 'admin/pedidos/:id',
    component: OwnerControlPedidoForm,
  },
  {
    path: 'admin/productos',
    component: OwnerControlProductos,
  },
  {
    path: 'admin/productos/agregar',
    component: OwnerControlProductoForm,
  },
  {
    path: 'admin/clientes',
    component: OwnerControlClienteForm,
  },
  {
    path: 'admin/clientes/agregar',
    component: OwnerControlClienteForm,
  },
  {
    path: 'cliente',
    component: CustomerControl,
  },
  {
    path: '**',
    component: Error,
  },
];
