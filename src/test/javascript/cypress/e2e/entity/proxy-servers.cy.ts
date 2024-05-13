import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('ProxyServers e2e test', () => {
  const proxyServersPageUrl = '/proxy-servers';
  const proxyServersPageUrlPattern = new RegExp('/proxy-servers(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const proxyServersSample = {};

  let proxyServers;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/proxy-servers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/proxy-servers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/proxy-servers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (proxyServers) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/proxy-servers/${proxyServers.id}`,
      }).then(() => {
        proxyServers = undefined;
      });
    }
  });

  it('ProxyServers menu should load ProxyServers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('proxy-servers');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ProxyServers').should('exist');
    cy.url().should('match', proxyServersPageUrlPattern);
  });

  describe('ProxyServers page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(proxyServersPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ProxyServers page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/proxy-servers/new$'));
        cy.getEntityCreateUpdateHeading('ProxyServers');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', proxyServersPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/proxy-servers',
          body: proxyServersSample,
        }).then(({ body }) => {
          proxyServers = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/proxy-servers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [proxyServers],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(proxyServersPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ProxyServers page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('proxyServers');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', proxyServersPageUrlPattern);
      });

      it('edit button click should load edit ProxyServers page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ProxyServers');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', proxyServersPageUrlPattern);
      });

      it('edit button click should load edit ProxyServers page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ProxyServers');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', proxyServersPageUrlPattern);
      });

      it('last delete button click should delete instance of ProxyServers', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('proxyServers').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', proxyServersPageUrlPattern);

        proxyServers = undefined;
      });
    });
  });

  describe('new ProxyServers page', () => {
    beforeEach(() => {
      cy.visit(`${proxyServersPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ProxyServers');
    });

    it('should create an instance of ProxyServers', () => {
      cy.get(`[data-cy="ipAddress"]`).type('underneath carload scale');
      cy.get(`[data-cy="ipAddress"]`).should('have.value', 'underneath carload scale');

      cy.get(`[data-cy="port"]`).type('15759');
      cy.get(`[data-cy="port"]`).should('have.value', '15759');

      cy.get(`[data-cy="country"]`).type('Israel');
      cy.get(`[data-cy="country"]`).should('have.value', 'Israel');

      cy.get(`[data-cy="protocol"]`).type('geez yowza divine');
      cy.get(`[data-cy="protocol"]`).should('have.value', 'geez yowza divine');

      cy.get(`[data-cy="anonymity"]`).type('natter');
      cy.get(`[data-cy="anonymity"]`).should('have.value', 'natter');

      cy.get(`[data-cy="organization"]`).type('tight for carefully');
      cy.get(`[data-cy="organization"]`).should('have.value', 'tight for carefully');

      cy.get(`[data-cy="speed"]`).type('13403');
      cy.get(`[data-cy="speed"]`).should('have.value', '13403');

      cy.get(`[data-cy="responseTime"]`).type('24666');
      cy.get(`[data-cy="responseTime"]`).should('have.value', '24666');

      cy.get(`[data-cy="successCount"]`).type('15852');
      cy.get(`[data-cy="successCount"]`).should('have.value', '15852');

      cy.get(`[data-cy="failCount"]`).type('2529');
      cy.get(`[data-cy="failCount"]`).should('have.value', '2529');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        proxyServers = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', proxyServersPageUrlPattern);
    });
  });
});
